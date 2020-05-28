package org.acme.rest.json;

import io.cloudevents.CloudEvent;
import io.cloudevents.v1.CloudEventBuilder;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.http.HttpClient;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Path("/api")
public class FactResource {

    @Inject
    @RestClient
    FactClient factClient;

    private static CloudEvent createCE(BigInteger n) {
        return CloudEvent
                .buildV1()
                .withData(String.valueOf(n).getBytes(StandardCharsets.UTF_8))
                .withType("fact")
                .withSource(URI.create("http://localhost"))
                .withDataContentType("text/plain")
                .withId(UUID.randomUUID().toString())
                .build();
    }

    private static BigInteger readFromCE(CloudEvent ce) {
        return new BigInteger(new String(ce.getData(), StandardCharsets.UTF_8));
    }

    @POST
    @Path("/fact")
    public Uni<CloudEvent> fact(CloudEvent ce) {

        BigInteger n = readFromCE(ce);

        if (n.compareTo(BigInteger.ONE) <= 0) {
            return Uni.createFrom().item(() -> createCE(BigInteger.ONE));
        }

        return factClient.fact(createCE(n.subtract(BigInteger.ONE)))
                .onItem()
                .apply(cloudEvent -> createCE(readFromCE(cloudEvent).multiply(n)));
    }
}

