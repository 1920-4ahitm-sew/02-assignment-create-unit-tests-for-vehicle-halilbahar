package at.htl.vehicle.rest;

import at.htl.vehicle.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class VehicleEndpointIT {
    private Client client;
    private WebTarget webTarget;

    @BeforeEach
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target("http://localhost:8080/vehicle/api/vehicle");
    }

    @Test
    public void getVehicles() {
        Response response = this.webTarget.request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray payload = response.readEntity(JsonArray.class);
        System.out.println("payload = " + payload);

        JsonObject vehicle = payload.getJsonObject(0);
        assertThat(vehicle.getString("brand"), is("Opel 42"));
        assertThat(vehicle.getString("type"), is("Commodore"));
    }

    @Test
    public void getVehicle() {
        // GET with id
        JsonObject dedicatedVehicle = this.webTarget
                .path("43")
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertThat(dedicatedVehicle.getString("brand"), containsString("43"));
        assertThat(dedicatedVehicle.getString("brand"), equalTo("Opel 43"));
    }

    @Test
    public void deleteVehicle() {
        Response deleteResponse = this.webTarget
                .path("42")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }

    @Test
    public void postVehicle() {
        Vehicle vehicle = new Vehicle("Opel", "Astra");
        Response postResponse = webTarget
                .request()
                .post(Entity.json(vehicle));
        System.out.println("postResponse = " + postResponse);
        assertThat(postResponse.getStatus(), is(204));
    }
}
