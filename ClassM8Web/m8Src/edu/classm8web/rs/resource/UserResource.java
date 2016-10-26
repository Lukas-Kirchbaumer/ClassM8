package edu.classm8web.rs.resource;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import edu.classm8web.dto.M8;
import edu.classm8web.rs.result.M8Result;

@Path("user")
public class UserResource {

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUsers(@Context Request request, @Context HttpServletRequest httpServletRequest, 
			@QueryParam("ids") String ids, @QueryParam("email") String email){
		
		M8 max = new M8();
		max.setEmail("max.alskdf@slkdfj.at");
		max.setId(1);
		max.setFirstname("Max");
		max.setLastname("Haider");
		max.setHasVoted(false);
		max.setPassword("danke");
		max.setVotes(0);
		
		M8 kramer = new M8();
		kramer.setEmail("kramer.alskdf@slkdfj.at");
		kramer.setId(2);
		kramer.setFirstname("Lukas");
		kramer.setLastname("Kramer");
		kramer.setHasVoted(true);
		kramer.setPassword("danke");
		kramer.setVotes(324);
		
		Vector<M8> m8s = new Vector<>();
		m8s.add(max);
		m8s.add(kramer);
		
		M8Result result = new M8Result();
		result.setSuccess(true);
		result.getContent().addAll(m8s);
		
		
		return Response.status(Response.Status.ACCEPTED).entity(result).build();	
	}
	
	//Hier könnte ihre Werbung stehen
	
	//TODO: Service imple
}
