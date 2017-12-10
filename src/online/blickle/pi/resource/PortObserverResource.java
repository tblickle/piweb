package online.blickle.pi.resource;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.blickle.pi.HardwareAccess;
import online.blickle.pi.PortDescriptionList;
import online.blickle.pi.observer.ObserverManager;
import online.blickle.pi.observer.PortObserver;


@Path("/observers")
public class PortObserverResource {

	@Context ServletContext servletContext;
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<PortObserver> getObserver() throws Exception {
		ObserverManager om = getObserverManager();
		return om.getAllObservers();
	}
		
	@Path("/{id}/")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPortStatus(@PathParam("id") String id) {
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
	public Response putNewObserver(PortObserver observer) {
		ObserverManager om = getObserverManager();
		String po= om.addObserver(observer);
		PortObserver pos = om.getObserver(po);
		return Response.ok(pos).status(Response.Status.CREATED).build();
	}
	

	
	private HardwareAccess getHardwareAccess() {
		return (HardwareAccess)servletContext.getAttribute(HardwareAccess.KEY);
	}
	
	private PortDescriptionList getPortConfiguration() {
		return (PortDescriptionList)servletContext.getAttribute(PortDescriptionList.KEY);
	}
	
	private ObserverManager getObserverManager() {
		return (ObserverManager)servletContext.getAttribute(ObserverManager.KEY);
	}
}
