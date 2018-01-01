package online.blickle.pi.resource;

import java.util.Collection;

import io.swagger.annotations.Api;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import online.blickle.pi.PortDescriptionList;

@Api(value = "/descriptions")
@Path("/descriptions")
public class ConfigurationResource {

@Context ServletContext servletContext;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "List configuration of all ports",    response = PortDescriptionList.class)
	
	public PortDescriptionList getPortConCollection() throws Exception {
		PortDescriptionList pc = getPortConfiguration(servletContext);
		return pc;
	}
	
	private PortDescriptionList getPortConfiguration(ServletContext context) {
		return (PortDescriptionList)context.getAttribute(PortDescriptionList.KEY);
	}
}
