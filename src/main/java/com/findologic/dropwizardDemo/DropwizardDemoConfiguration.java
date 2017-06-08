package com.findologic.dropwizardDemo;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.JerseyClientConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DropwizardDemoConfiguration extends Configuration {
    @NotEmpty
    private String graphiteHost;

    private int graphitePort;

    @NotEmpty
    private String fortuneApiUrl;

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();

    @JsonProperty
    public String getGraphiteHost() {
        return graphiteHost;
    }

    @JsonProperty
    public void setGraphiteHost(String graphiteHost) {
        this.graphiteHost = graphiteHost;
    }

    @JsonProperty
    public int getGraphitePort() {
        return graphitePort;
    }

    @JsonProperty
    public void setGraphitePort(int graphitePort) {
        this.graphitePort = graphitePort;
    }

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClientConfiguration;
    }

    @JsonProperty
    public String getFortuneApiUrl() {
        return fortuneApiUrl;
    }

    @JsonProperty
    public void setFortuneApiUrl(String fortuneApiUrl) {
        this.fortuneApiUrl = fortuneApiUrl;
    }
}
