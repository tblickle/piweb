package online.blickle.pi.observer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/receive")
public class DummyObserverReceiver {

	@Path("/{id}/")
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public Response postChangeEvent(@PathParam("id") String id,String  newStatus) throws IOException {
		Logger.getLogger (DummyObserverReceiver.class.getName()).log(Level.INFO,"Port change POST "+id +" received: "+newStatus);
		return Response.ok().build();
	}
	
	@Path("/{id}/")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPortStatus(@PathParam("id") String id) {
		Logger.getLogger (DummyObserverReceiver.class.getName()).log(Level.INFO,"Port change GET "+id +" received:");
		return Response.ok().build();
	}
}
