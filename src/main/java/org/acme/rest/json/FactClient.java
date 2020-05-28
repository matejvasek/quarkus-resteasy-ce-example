package org.acme.rest.json;

import io.cloudevents.CloudEvent;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api")
@RegisterRestClient(configKey="fact-api")
public interface FactClient {

    @POST
    @Path("/fact")
    Uni<CloudEvent> fact(CloudEvent ce);

}
