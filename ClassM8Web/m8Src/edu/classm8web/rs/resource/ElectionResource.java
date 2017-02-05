package edu.classm8web.rs.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.persistence.indirection.IndirectList;

import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dao.SchoolclassService;
import edu.classm8web.database.dto.M8;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.exception.DatabaseException;
import edu.classm8web.rs.result.M8Result;
import edu.classm8web.rs.result.Result;

@Path("election")
public class ElectionResource extends AbstractResource {

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response vote(@Context HttpServletRequest httpServletRequest, @QueryParam("voterid") String voterM8, @QueryParam("votedid") String votedM8) {
		logMessage(this.getClass(), httpServletRequest, "vote");
		workaround();
		Result result = new Result();

		try {
			M8 voter = MateService.getInstance().findById(Long.parseLong(voterM8));
			M8 voted = MateService.getInstance().findById(Long.parseLong(votedM8));

			if (voter.getSchoolclass().getId() == voted.getSchoolclass().getId() && !voter.isHasVoted()) {
				Schoolclass sc = SchoolclassService.getInstance().findById(voter.getSchoolclass().getId());
				if(sc != null){
					voter.setHasVoted(true);
					int votes = voted.getVotes() + 1;
					voted.setVotes(votes);
					
					
					MateService.getInstance().update(voter);
					MateService.getInstance().update(voted);
					
					List<M8> comp = sc.getClassMembers();
					
					if (comp instanceof IndirectList) {
					    IndirectList iList = (IndirectList)comp;
					    Object sortTargetObject = iList.getDelegateObject();
					    if (sortTargetObject instanceof List<?>) {
					        List<M8> sortTarget=(List<M8>) sortTargetObject;
					        Collections.sort(sortTarget, new Comparator<M8>() {

								@Override
								public int compare(M8 m1, M8 m2) {
									return m2.getVotes() - m1.getVotes();
								}
							});
					    }
					} else {
					    Collections.sort(comp, new Comparator<M8>() {

							@Override
							public int compare(M8 m1, M8 m2) {
								return m2.getVotes() - m1.getVotes();
							}
						});
					}
					

					
					result.setSuccess(true);
				}
				else{
					throw new Exception("Nobody is in a schoolclass");
				}

			} else {
				throw new Exception("Voter has already voted or Voter is not in the same class as the M8 he voted for");
			}

		} catch (Exception e) {
			handelAndThrowError(e, result);
		}

		

		return Response.status(Response.Status.ACCEPTED).entity(result).build();
	}
	
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPresidents(@Context HttpServletRequest httpServletRequest, @QueryParam("scid") String scid) throws Exception{
		
		Result r = new Result();
		
		Schoolclass sc = SchoolclassService.getInstance().findById(Long.valueOf(scid));
		
		List<M8> comp = sc.getClassMembers();
		
		if (comp instanceof IndirectList) {
		    IndirectList iList = (IndirectList)comp;
		    Object sortTargetObject = iList.getDelegateObject();
		    if (sortTargetObject instanceof List<?>) {
		        List<M8> sortTarget=(List<M8>) sortTargetObject;
		        Collections.sort(sortTarget, new Comparator<M8>() {

					@Override
					public int compare(M8 m1, M8 m2) {
						return m2.getVotes() - m1.getVotes();
					}
				});
		    }
		} else {
		    Collections.sort(comp, new Comparator<M8>() {

				@Override
				public int compare(M8 m1, M8 m2) {
					return m2.getVotes() - m1.getVotes();
				}
			});
		}
		
		if(comp.size() >= 2){
			sc.setPresident(comp.get(0));
			sc.setPresidentDeputy(comp.get(1));
		}

		
		
		SchoolclassService.getInstance().update(sc);
		r.setSuccess(true);
		
		return Response.status(Status.ACCEPTED).entity(r).build();
	}



}
