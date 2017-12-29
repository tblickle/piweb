package online.blickle.pi.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.blickle.pi.RaspberryPort;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RestPortTest {
	

	@Test
	public void port_setPortOutputActiveAndReadStatus_StatusIsActive() {
		WebTarget target = RestTestHelper.getWebTarget_outputPort();
		String status = "true";
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(status, MediaType.APPLICATION_JSON_TYPE));
		assertTrue("Request should be accepted as port is ouput port.",response.getStatus()==201);
		RaspberryPort portStatus= response.readEntity(RaspberryPort.class);
		assertTrue("Portvalue not as expected.",portStatus.isValue());
		
	}
	
	@Test
	public void port_setInportPortActive_HTTP400Returned() {
		WebTarget target = RestTestHelper.getWebTarget_inputPort();
		String status = "true";
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(status, MediaType.APPLICATION_JSON_TYPE));
		assertTrue("Request should be rejected as port is input port.",response.getStatus()==400);
		
	}
	
	@Test
	public void port_toggleOutputPortStatus_statusToggled() {
		RaspberryPort portStatus = RestTestHelper.call_get_ports(RestTestHelper.OUTPUT_PORT);
		boolean startState = portStatus.isValue();
		assertTrue(RestTestHelper.call_post_ports(RestTestHelper.OUTPUT_PORT, !startState));
		RaspberryPort portStatus2 = RestTestHelper.call_get_ports(RestTestHelper.OUTPUT_PORT);
		boolean endState = portStatus2.isValue();
		assertTrue(startState!=endState);
		
	}
}
