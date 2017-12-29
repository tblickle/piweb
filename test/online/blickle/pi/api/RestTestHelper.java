package online.blickle.pi.api;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.blickle.pi.RaspberryPort;

public class RestTestHelper {

	public static final String SERVER_URL = "http://localhost:8080/piweb/v1";
	public static final String OUTPUT_PORT = "LICHT_TERRASSE";
	public static final String INPUT_PORT = "INPUT_4";
	
	
	public static RaspberryPort call_get_ports(String portId) {
		WebTarget target = getWebTarget_ports(portId);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		if (response.getStatus()!=200) {
			throw new IllegalStateException("Status Response != 200");
		}
		RaspberryPort portStatus= response.readEntity(RaspberryPort.class);
		return portStatus;
	}
	public static boolean call_post_ports(String portId, boolean status) {
		WebTarget target = getWebTarget_ports(portId);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(status, MediaType.APPLICATION_JSON_TYPE));
		return response.getStatus()==201;	
	}
	
	
	public static WebTarget getWebTarget() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SERVER_URL);
		return target;
	}
	
	public static WebTarget getWebTarget_ports(String portId) {
		return getWebTarget().path("ports").path(portId);
	}
	
	public static WebTarget getWebTarget_inputPort() {
		return getWebTarget_ports(INPUT_PORT);
	}
	
	public static WebTarget getWebTarget_outputPort() {
		return getWebTarget_ports(OUTPUT_PORT);
	}
}
