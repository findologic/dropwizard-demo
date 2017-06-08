package com.findologic.dropwizardDemo.health;

import com.codahale.metrics.health.HealthCheck;
import com.findologic.dropwizardDemo.api.Fortune;
import com.findologic.dropwizardDemo.resources.FortuneResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FortuneHealthCheckTest {

    private FortuneHealthCheck healthCheck;
    private FortuneResource mockResource;

    @Before
    public void setUp() {
        mockResource = mock(FortuneResource.class);
        healthCheck = new FortuneHealthCheck(mockResource);
    }

    @Test
    public void testHealthCheckSucceedsIfFortuneIsReturned() throws Exception {
        when(mockResource.getFortune()).thenReturn(new Fortune() {{
            setFortune("Man who makes mistake in elevator is wrong on many levels.");
        }});

        Assert.assertEquals(HealthCheck.Result.healthy(), healthCheck.check());
    }

    @Test
    public void testHealthCheckFailsIfFortuneTextIsNull() throws Exception {
        when(mockResource.getFortune()).thenReturn(new Fortune() {{
            setFortune(null);
        }});

        Assert.assertEquals(HealthCheck.Result.unhealthy("Fortune text is null."), healthCheck.check());
    }

    @Test
    public void testHealthCheckFailsInCaseOfException() throws Exception {
        String expectedMessage = "Things went horribly wrong.";
        when(mockResource.getFortune()).thenThrow(new RuntimeException(expectedMessage));

        Assert.assertEquals(HealthCheck.Result.unhealthy(expectedMessage), healthCheck.check());
    }
}
