package edu.classm8web.rs.resource;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import edu.classm8web.dao.Database;
import edu.classm8web.dto.M8;
import edu.classm8web.rs.result.M8Result;
import edu.classm8web.services.UserService;

@Path("user")
public class UserResource {

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUsers(@Context Request request, @Context HttpServletRequest httpServletRequest) {

		M8Result result = new M8Result();
		result.setSuccess(true);
		result.getContent().addAll(Database.getInstance().getMades().values());

		return Response.status(Response.Status.ACCEPTED).entity(result).build();
	}

	@PUT
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response update(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id, final M8 input) {

		M8 m8 = input;
		m8.setId(Integer.parseInt(id));

		UserService.getInstance().updateUser(m8);

		return Response.status(Response.Status.ACCEPTED).build();
	}

	@POST
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response create(final M8 input) {
		UserService.getInstance().registerUser(input);
		return Response.status(Response.Status.ACCEPTED).build();
	}
	
	@DELETE
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response delete(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id) {


		UserService.getInstance().deleteUser(Integer.parseInt(id));

		return Response.status(Response.Status.ACCEPTED).build();
	}

}
