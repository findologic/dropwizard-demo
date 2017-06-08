package com.findologic.dropwizardDemo;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.findologic.dropwizardDemo.client.FortuneClient;
import com.findologic.dropwizardDemo.health.FortuneHealthCheck;
import com.findologic.dropwizardDemo.resources.FortuneResource;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Client;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class DropwizardDemoApplication extends Application<DropwizardDemoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropwizardDemoApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizardDemo";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardDemoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final DropwizardDemoConfiguration configuration,
                    final Environment environment) {
        enableGraphiteReports(configuration, environment);

        final Client restClient = registerRestClient(configuration, environment);
        registerFortuneResource(configuration, environment, restClient);
    }

    private void registerFortuneResource(final DropwizardDemoConfiguration configuration, final Environment environment,
                                         final Client restClient) {
        // The resource requires the client for the external Fortune service.
        final FortuneClient fortuneClient = new FortuneClient(restClient, configuration.getFortuneApiUrl());

        // The resource itself.
        final FortuneResource fortuneResource = new FortuneResource(fortuneClient);
        environment.jersey().register(fortuneResource);

        // The health check for it.
        final FortuneHealthCheck fortuneHealthCheck = new FortuneHealthCheck(fortuneResource);
        environment.healthChecks().register("fortune", fortuneHealthCheck);
    }

    private Client registerRestClient(final DropwizardDemoConfiguration configuration, final Environment environment) {
        final Client restClient = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());
        environment.jersey().register(restClient);

        return restClient;
    }

    private void enableGraphiteReports(final DropwizardDemoConfiguration configuration, final Environment environment) {
        final Graphite graphiteClient = new Graphite(
                new InetSocketAddress(configuration.getGraphiteHost(), configuration.getGraphitePort()));

        final GraphiteReporter reporter = GraphiteReporter.forRegistry(environment.metrics())
                .prefixedWith("dropwizard-demo")
                        // All rate units should be something per second.
                .convertRatesTo(TimeUnit.SECONDS)
                        // We're going fast, so let's measure durations in milliseconds.
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                        // For demo purposes, let's collect all metrics. In production, you might want to use a more specific
                        // filter to increase performance.
                .filter(MetricFilter.ALL)
                .build(graphiteClient);

        // Push metrics to Graphite every ten seconds.
        reporter.start(10, TimeUnit.SECONDS);
    }
}
