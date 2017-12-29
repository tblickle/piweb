package online.blickle.pi.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import online.blickle.pi.observer.ChangeEventData;
import online.blickle.pi.observer.PortObserver;
import online.blickle.pi.observer.PortObserverChangeListener;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestPortObserverTest {

	@Test
	public void PoService_addPortObserver_poIDreturned() {
		PortObserver po = new PortObserver("LICHT_TERRASSE","http://localhost:8080/piweb/v1/receive/LICHT_TERRASSE");
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
	
	@Test
	public void receiveTest() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/piweb/v1/receive/LICHT_TERRASSE");
		ChangeEventData ced = new ChangeEventData("XXX",false);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(ced, MediaType.APPLICATION_JSON_TYPE));
		Logger.getLogger (PortObserverChangeListener.class.getName()).log(Level.INFO,"HTTP status :"+response.toString());
	}
	
	@Test
	public void receiveTest2() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/piweb/v1/receive/LICHT_TERRASSE");
		ChangeEventData ced = new ChangeEventData("XXX",false);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		Logger.getLogger (PortObserverChangeListener.class.getName()).log(Level.INFO,"HTTP status :"+response.toString());
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
