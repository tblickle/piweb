package online.blickle.pi.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import online.blickle.pi.HardwareAccess;
import online.blickle.pi.PortDescription;
import online.blickle.pi.PortDescriptionList;
import online.blickle.pi.RaspberryPort;



@Path("/ports")
public class HardwareResoure {
	
	@Context ServletContext servletContext;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<RaspberryPort> getPortStatus() throws Exception {
	
		HardwareAccess ctrl = getHardwareAccess();
		PortDescriptionList pc = getPortConfiguration();
		
		Collection<RaspberryPort> pL = new ArrayList<RaspberryPort>();
		for (PortDescription portDesc:pc.getPortDescriptions()) {
			pL.add(new RaspberryPort(portDesc, ctrl.getPortStatus(portDesc)));
		}
		return pL;
	}
	
	@Path("/{id}/")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public RaspberryPort getPortStatus(@PathParam("id") String id) throws IOException {
	
		HardwareAccess ctrl = getHardwareAccess();
		PortDescriptionList pc = getPortConfiguration();
		PortDescription port =  pc.getPortDecriptionById(id);
		
		RaspberryPort p = new RaspberryPort(port, ctrl.getPortStatus(port));
		
		return p;
	}
	
	@Path("/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public Response setPortStatus(@PathParam("id") String id,String value) throws IOException {
	
		boolean portStatus = extractPortStatus(value);
		HardwareAccess ctrl = getHardwareAccess();
		PortDescriptionList pc = getPortConfiguration();
		PortDescription port =  pc.getPortDecriptionById(id);
		if (port.getIsOutput()) {
			if (portStatus) {
				ctrl.setPort(port);
				Logger.getLogger (HardwareResoure.class.getName()).log(Level.INFO,"Set port "+port.getId());
			} else  {
				ctrl.clearPort(port);
				Logger.getLogger (HardwareResoure.class.getName()).log(Level.INFO,"Cleared port "+port.getId());
			}
			RaspberryPort p = new RaspberryPort(port, ctrl.getPortStatus(port));
			Logger.getLogger (HardwareResoure.class.getName()).log(Level.INFO,"Port: "+p);
		
			return Response.ok(p).status(Response.Status.CREATED).build();	
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
	}

	private boolean extractPortStatus(String value) {
		boolean portStatus = true;
		if ("0".equals(value) || "false".equalsIgnoreCase(value)) {
			portStatus = false;
		} else if ("1".equals(value) || "true".equalsIgnoreCase(value)) {
			portStatus = true;
		} else {
			throw new IllegalArgumentException("Only '0' and '1' or 'true' and 'false' allowed as port status values");
		}
		return portStatus;
	}
	
	
	private HardwareAccess getHardwareAccess() {
		return (HardwareAccess)servletContext.getAttribute(HardwareAccess.KEY);
	}
	
	private PortDescriptionList getPortConfiguration() {
		return (PortDescriptionList)servletContext.getAttribute(PortDescriptionList.KEY);
	}
	
}
