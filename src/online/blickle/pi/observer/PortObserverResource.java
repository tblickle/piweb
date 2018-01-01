package online.blickle.pi.observer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.blickle.pi.RaspberryPort;


@Path("/observers")
@Api(value = "/observers")
public class PortObserverResource {

	@Context ServletContext servletContext;
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "List all registered port obeservers",  notes = "Returns status of single port",  response = RaspberryPort.class)
	public Collection<PortObserver> getAllObservers() throws Exception {
		ObserverManager om = getObserverManager();
		return om.getAllObservers();
	}
		
	@Path("/{id}/")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Find observer by observer-ID",  notes = "Returns info of single observer",  response = PortObserver.class)
	public Response getObserver( @ApiParam(value = "ID of observer") @PathParam("id") String id) {
		ObserverManager om = getObserverManager();
		PortObserver po= om.getObserver(id);
		if (po==null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(po).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Register observer",  notes = "Registers a new observer",  response = PortObserver.class)
	public Response putNewObserver(@ApiParam(value = "New observer") PortObserver observer) {
		ObserverManager om = getObserverManager();
		String po= om.addObserver(observer);
		PortObserver pos = om.getObserver(po);
		return Response.ok(pos).status(Response.Status.CREATED).build();
	}
	
	@Path("/{id}/")
	@DELETE
	@ApiOperation(value = "Unregister observer",  notes = "Removes the observer with the given ID") 
	public Response deleteObserver(@ApiParam(value = "ID of observer") @PathParam("id") String id) {
		ObserverManager om = getObserverManager();
		PortObserver po= om.getObserver(id);
		if (po==null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		om.removeObserver(id);
		return Response.ok().build();
	}
	
	private ObserverManager getObserverManager() {
		return (ObserverManager)servletContext.getAttribute(ObserverManager.KEY);
	}
}
