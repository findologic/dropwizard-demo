package com.findologic.dropwizardDemo.health;

import com.codahale.metrics.health.HealthCheck;
import com.findologic.dropwizardDemo.api.Fortune;
import com.findologic.dropwizardDemo.resources.FortuneResource;

public class FortuneHealthCheck extends HealthCheck {

    private final FortuneResource fortuneResource;

    public FortuneHealthCheck(FortuneResource fortuneResource) {
        this.fortuneResource = fortuneResource;
    }

    @Override
    protected Result check() throws Exception {
        try {
            Fortune fortune = fortuneResource.getFortune();

            if (fortune.getFortune() == null) {
                return Result.unhealthy("Fortune text is null.");
            } else {
                return Result.healthy();
            }
        } catch (Exception e) {
            return Result.unhealthy(e.getMessage());
        }
    }
}
