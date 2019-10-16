package at.htl.vehicle.rest;

import org.junit.Before;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VehicleEndpointIT {
    private Client client;
    private WebTarget webTarget;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target("http://localhost:8080/vehicle/api/vehicle");
    }

    @Test
    public void fetchVehicle() {
        Response response = this.webTarget.request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray payload = response.readEntity(JsonArray.class);
        System.out.println("payload = " + payload);

        JsonObject vehicle = payload.getJsonObject(0);
        assertThat(vehicle.getString("brand"), is("Opel 42"));
        assertThat(vehicle.getString("type"), is("Commodore"));
    }
}
