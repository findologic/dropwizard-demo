package com.findologic.dropwizardDemo.client;

import com.findologic.dropwizardDemo.api.Fortune;

import javax.ws.rs.client.Client;

public class FortuneClient {

    private final String fortuneApiUrl;

    private final Client restClient;

    public FortuneClient(Client restClient, String fortuneApiUrl) {
        this.restClient = restClient;
        this.fortuneApiUrl = fortuneApiUrl;
    }

    public Fortune getFortune() {
        return restClient.target(fortuneApiUrl)
                .request()
                .get(Fortune.class);
    }
}
