package com.findologic.dropwizardDemo;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
