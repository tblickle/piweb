package online.blickle.pi.resource;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import online.blickle.pi.PortDescriptionList;

@Path("/configuration")
public class ConfigurationResource {

@Context ServletContext servletContext;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public PortDescriptionList getPortConCollection() throws Exception {
	
		PortDescriptionList pc = getPortConfiguration(servletContext);
		
		return pc;
	}
	
	private PortDescriptionList getPortConfiguration(ServletContext context) {
		return (PortDescriptionList)context.getAttribute(PortDescriptionList.KEY);
	}
}
