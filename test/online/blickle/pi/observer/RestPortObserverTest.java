package online.blickle.pi.observer;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestPortObserverTest {

	@Test
	public void PoService_addPortObserver_poIDreturned() {
		PortObserver po = new PortObserver("LICHT_TERRASSE","x");
		Response response = registerPortObserver(po);
		assertTrue(response.getStatus()==201);
		String poID=response.readEntity(String.class);
		//
		
	}
	
	@Test
	public void poService_readExistingObserver_observerInfoReturned() {
		Response res = readPortObserver("ID0");
		assertTrue(res.getStatus()==200);
		PortObserver po = res.readEntity(PortObserver.class);
		assertTrue(po != null);
		System.out.println(po);
		
	}
	
	@Test
	public void poService_readNonExistingObserver_HTTP404_Returned() {
		Response res = readPortObserver("THIS_ID_DOES_NOT_EXIT");
		assertTrue(res.getStatus()==404);
		
		
	}
	
	private Response registerPortObserver(PortObserver observer) {
		WebTarget target = RestTestHelper.getWebTarget().path("observers");
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(observer, MediaType.APPLICATION_JSON_TYPE));
		return response;
	}
	
	private Response readPortObserver(String pid) {
		WebTarget target = RestTestHelper.getWebTarget().path("observers").path(pid);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		return response;
	}
}
