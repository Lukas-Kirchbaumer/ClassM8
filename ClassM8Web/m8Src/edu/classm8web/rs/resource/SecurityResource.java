package edu.classm8web.rs.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.classm8web.database.dto.M8;
import edu.classm8web.rs.result.LoginResult;
import edu.classm8web.services.SecurityService;

@Path("login")
public class SecurityResource extends AbstractResource {

	@POST
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response login(@Context HttpServletRequest httpServletRequest, final M8 input) {

		logMessage(this.getClass(), httpServletRequest, "Login");

		
		LoginResult res = new LoginResult();

		try {
			long ret = SecurityService.getInstance().checkLogin(input.getEmail(), input.getPassword());
			res.setId(ret);
			res.setSuccess(true);

		} catch (Exception e) {
			handelAndThrowError(e, res);
		}

		return Response.status(Response.Status.ACCEPTED).entity(res).build();
	}
}
