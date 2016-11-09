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
import edu.classm8web.dto.Schoolclass;
import edu.classm8web.mapper.ObjectMapper;
import edu.classm8web.mapper.objects.MappedSchoolclass;
import edu.classm8web.rs.result.SchoolclassResult;
import edu.classm8web.services.SchoolclassService;

@Path("schoolclass")
public class SchoolclassResource {
	
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllSchoolclasses(@Context Request request, @Context HttpServletRequest httpServletRequest) {
		
		Vector<MappedSchoolclass> resObject = new Vector<>();
		resObject.addAll(ObjectMapper.map(SchoolclassService.getInstance().getAllSchoolClasses()));
		
		SchoolclassResult res = new SchoolclassResult();	
		res.setSchoolclasses(resObject);
		res.setSuccess(true);

		return Response.status(Response.Status.ACCEPTED).entity(res).build();
	}

	@PUT
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateSchoolclass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id, final Schoolclass input) {

		Schoolclass sc = new Schoolclass();
		sc.setId(Long.parseLong(id));

		SchoolclassService.getInstance().updateSchoolclass(sc);

		return Response.status(Response.Status.ACCEPTED).build();
	}

	@POST
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createSchoolclass(final Schoolclass input, @QueryParam("m8id") String id) {
		SchoolclassService.getInstance().registerSchoolclass(input,Long.parseLong(id));
		return Response.status(Response.Status.ACCEPTED).build();
	}
	
	@DELETE
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteSchoolclass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id) {


		SchoolclassService.getInstance().deleteSchoolclass(Long.parseLong(id));

		return Response.status(Response.Status.ACCEPTED).build();
	}
	
	@GET
	@Path("m8")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getClassbyUser(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("m8id") String id) {
		
		
		Schoolclass sc = SchoolclassService.getInstance().getSchoolClassByM8(Long.parseLong(id));
		Vector<MappedSchoolclass> resObject = new Vector<>();
		resObject.add(ObjectMapper.map(sc));
		
		SchoolclassResult res = new SchoolclassResult();	
		res.setSchoolclasses(resObject);
		res.setSuccess(true);

		return Response.status(Response.Status.ACCEPTED).entity(res).build();
	}
	
	@POST
	@Path("m8")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addM8ToSchoolClass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("m8id") String m8id,@QueryParam("scid") String scid) {


		SchoolclassService.getInstance().addM8ToSchoolClass(Long.parseLong(scid), Long.parseLong(m8id));

		return Response.status(Response.Status.ACCEPTED).build();
	}
	@DELETE
	@Path("m8")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response removeM8fromSchoolClass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("m8id") String m8id,@QueryParam("scid") String scid){
		
		SchoolclassService.getInstance().removeM8FromSchoolClass(Long.parseLong(scid), Long.parseLong(m8id));

		return Response.status(Response.Status.ACCEPTED).build();
	}
}
