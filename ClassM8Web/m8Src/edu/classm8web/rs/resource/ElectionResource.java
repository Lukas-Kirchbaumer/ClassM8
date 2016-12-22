package edu.classm8web.rs.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
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
import edu.classm8web.mapper.ObjectMapper;
import edu.classm8web.rs.result.M8Result;

@Path("election")
public class ElectionResource extends AbstractResource {

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response vote(@QueryParam("VoterM8") String voterM8, @QueryParam("VotedM8") String votedM8) {
		workaround();
		M8Result result = new M8Result();

		try {
			M8 voter = MateService.getInstance().findById(Long.parseLong(voterM8));
			M8 voted = MateService.getInstance().findById(Long.parseLong(votedM8));

			if (voter.getSchoolclass().getId() == voted.getSchoolclass().getId() && !voter.isHasVoted()) {

				voter.setHasVoted(true);
				int votes = voted.getVotes() + 1;
				voted.setVotes(votes);
				result.setSuccess(true);
			} else {
				throw new Exception("Voter has already voted or Voter is not in the same class as the M8 he voted for");
			}

		} catch (Exception e) {
			handelAndThrowError(e, result);
		}

		return Response.status(Response.Status.ACCEPTED).entity(result).build();
	}



}
