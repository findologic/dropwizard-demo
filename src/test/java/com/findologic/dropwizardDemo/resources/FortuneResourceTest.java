package com.findologic.dropwizardDemo.resources;

import com.findologic.dropwizardDemo.api.Fortune;
import com.findologic.dropwizardDemo.client.FortuneClient;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.ServiceUnavailableException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class FortuneResourceTest {

    private static final FortuneClient mockFortuneClient = mock(FortuneClient.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new FortuneResource(mockFortuneClient))
            .build();

    @Before
    public void setUp() {
        reset(mockFortuneClient);
    }

    @Test
    public void testFortuneIsReturned() {
        final String expectedFortuneText = "There is no free lunch.";

        when(mockFortuneClient.getFortune()).thenReturn(new Fortune() {{
            setFortune(expectedFortuneText);
        }});

        final Fortune fortune = resources.client().target("/fortunes").request().get(Fortune.class);

        Assert.assertEquals(expectedFortuneText, fortune.getFortune());
    }

    @Test
    public void testUpstreamErrorLeadsTo503ServiceUnavailable() {
        when(mockFortuneClient.getFortune()).thenThrow(new ProcessingException("Connection refused."));

        try {
            resources.client().target("/fortunes").request().get(Fortune.class);
            Assert.fail();
        } catch (ServiceUnavailableException e) {
            // That's exactly what we want.
        }
    }
}
