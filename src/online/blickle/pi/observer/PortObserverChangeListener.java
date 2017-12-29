package online.blickle.pi.observer;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.blickle.pi.PortChangeEvent;
import online.blickle.pi.PortChangeListener;
import online.blickle.pi.PortDescription;

public class PortObserverChangeListener implements PortChangeListener{

	private PortObserver portObserver;
	private int failCounter = 0;
	
	public PortObserverChangeListener(PortObserver po) {
		this.portObserver = po;
	}
	
	@Override
	public void handlePortChangeEvent(PortChangeEvent event) {
		if (performCallback(event)) {
				failCounter=0;
			} else {
				failCounter++;
			}
	}
	
	protected boolean performCallback(PortChangeEvent event) {
		PortDescription port =  event.getPort();
		Logger.getLogger (PortObserverChangeListener.class.getName()).log(Level.INFO,"Port change "+port.getId() +" send to "+portObserver.getObserverURL());
		String data = Boolean.toString(event.isPosTrigger());
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(portObserver.getObserverURL());
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(data, MediaType.APPLICATION_JSON_TYPE));
		
		if (response.getStatus()>299) {
			Logger.getLogger (PortObserverChangeListener.class.getName()).log(Level.WARNING,"Port change callback failed (counter="+failCounter+"):"+response.toString());
			return false;
		} else {
			Logger.getLogger (PortObserverChangeListener.class.getName()).log(Level.INFO,"Port change call status :"+response.toString());
			return true;
		}
	}
	
	public PortObserver getPortObserver() {
		return portObserver;
	}
	
	public int getFailCounter() {
		return failCounter;
	}
	
	

}
