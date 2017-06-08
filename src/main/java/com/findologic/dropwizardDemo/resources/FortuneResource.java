package com.findologic.dropwizardDemo.resources;

import com.codahale.metrics.annotation.Timed;
import com.findologic.dropwizardDemo.api.Fortune;
import com.findologic.dropwizardDemo.client.FortuneClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;

@Path("/fortunes")
@Produces(MediaType.APPLICATION_JSON)
public class FortuneResource {

    private FortuneClient client;

    public FortuneResource(FortuneClient client) {
        this.client = client;
    }

    @GET
    @Timed
    public Fortune getFortune() {
        try {
            return client.getFortune();
        } catch (Exception e) {
            throw new ServiceUnavailableException();
        }
    }
}
