package org.knoesis.cevo.resources;


import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.*;

import org.knoesis.cevo.model.Event;
import org.knoesis.cevo.service.EventService;

@Path("/")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@CrossOriginResourceSharing(allowAllOrigins=true,allowOrigins="*")
public class EventResource {
	
	EventService eventService = new EventService();
	
	@POST
	public List<Event> getEvents(String inputText){
		return eventService.getAllEvents(inputText);
	}
}
