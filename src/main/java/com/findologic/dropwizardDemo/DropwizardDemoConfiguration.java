package com.findologic.dropwizardDemo;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class DropwizardDemoConfiguration extends Configuration {
    @NotEmpty
    private String graphiteHost;

    private int graphitePort;

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
}
