package edu.classm8web.rs.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dto.M8;
import edu.classm8web.rs.result.M8Result;
import edu.classm8web.rs.result.Result;

@Path("user")
public class UserResource extends AbstractResource {

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUsers(@Context Request request, @Context HttpServletRequest httpServletRequest) {

		M8Result result = new M8Result();

		try {
			result.setSuccess(true);
			result.getContent().addAll(MateService.getInstance().findAll());
		} catch (Exception e) {
			handelAndThrowError(e, result);
		}

		return Response.status(Response.Status.ACCEPTED).entity(result).build();
	}

	@PUT
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response update(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id, final M8 input) {

		Result r = new Result();

		try {
			M8 m8 = MateService.getInstance().findById(Long.parseLong(id));
			m8.setNewM8(input);
			MateService.getInstance().update(m8);
			r.setSuccess(true);
		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@POST
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response create(final M8 input) {

		Result r = new Result();

		try {
			input.setVotes(0);
			input.setHasVoted(false);
			MateService.getInstance().persist(input);
			r.setSuccess(true);
		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@DELETE
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response delete(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id) {

		Result r = new Result();

		try {
			MateService.getInstance().removeById(Long.parseLong(id));
			r.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			handelAndThrowError(e, r);
		}

		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@GET
	@Path("{id}")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@PathParam("id") String id) {

		M8Result result = new M8Result();

		try {
			result.setSuccess(true);
			result.getContent().add(MateService.getInstance().findById(Long.parseLong(id)));
		} catch (Exception e) {
			handelAndThrowError(e, result);
		}

		return Response.status(Response.Status.ACCEPTED).entity(result).build();
	}

}
