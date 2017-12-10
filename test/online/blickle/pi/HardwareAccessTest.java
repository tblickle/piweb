package online.blickle.pi;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.owlike.genson.Genson;

public class HardwareAccessTest {

	Genson genson;
	Client client;
	public static final String BASE_URL="http://localhost:8080/piweb/v1";
	
	@Before
	public void init() {
		genson = new Genson();
		client = ClientBuilder.newClient();
	}
	
	
	@Test
	public void portTest_readLICHT_TERRASSE() {
		 RaspberryPort p = readPort("LICHT_TERRASSE");
		 System.out.println("Port "+p.getId()+"="+p.isValue());
	
	}
	
	@Test
	public void portTest_readSTECKDOSE_TERRASSE() {
		 RaspberryPort p = readPort("STECKDOSE_TERRASSE");
		 System.out.println("Port "+p.getId()+"="+p.isValue());
	}
	
	@Test 
	public void portTest_togglePortStatusOfOutputPort_statusToggled() {
		PortDescriptionList config = readPortConfiguration();
		int counter=0;
		for (PortDescription desc: config.getPortDescriptions()) {
			if (desc.getIsOutput()) {
				boolean current_state = readPort(desc.getId()).isValue();
				boolean new_state = setPort(desc.getId(), !current_state).isValue();
				assertTrue("State of port '"+desc.getId()+"' not toggled.",new_state != current_state);
				counter++;
			}
		}
		assertTrue("No output port checked", counter>0);
	}
	
	@Test
	public void portTest_accessEachPort_valueReturned() {
		PortDescriptionList config = readPortConfiguration();
		for (PortDescription desc: config.getPortDescriptions()) {
			try {
				RaspberryPort p = readPort(desc.getId());
				assertTrue("Return port value is from requested port", p.getId().equals(desc.getId()));
			} catch (Exception ex) {
				fail("Could not read data from port "+desc.getId());
			}
		}
	}
	
	private RaspberryPort readPort(String portId) {
		WebTarget target = client.target(BASE_URL+"/ports/"+portId);
		Invocation.Builder invBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invBuilder.get();
		String json = res.readEntity(String.class);
		RaspberryPort p = genson.deserialize(json, RaspberryPort.class);
		return p;
	}
	
	private RaspberryPort setPort(String portId, boolean value) {
		System.out.println("Setport '"+portId+"'="+value);
		WebTarget target = client.target(BASE_URL+"/ports/"+portId);
		Invocation.Builder invBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invBuilder.post(Entity.json(value));
		String json = res.readEntity(String.class);
		RaspberryPort p = genson.deserialize(json, RaspberryPort.class);
		return p;
	}
	
	private PortDescriptionList readPortConfiguration() {
		
		WebTarget target = client.target(BASE_URL+"/configuration");
		Invocation.Builder invBuilder = target.request(MediaType.APPLICATION_JSON);
		Response res = invBuilder.get();
		String json = res.readEntity(String.class);
		PortDescriptionList config = genson.deserialize(json, PortDescriptionList.class);
		return config;
	}

}
