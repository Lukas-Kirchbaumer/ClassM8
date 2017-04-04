package edu.classm8web.rs.resource;

import edu.classm8web.rs.result.Point;
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
import javax.ws.rs.core.Response.Status;

import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dao.SchoolclassService;
import edu.classm8web.database.dto.M8;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.mapper.ObjectMapper;
import edu.classm8web.mapper.objects.MappedM8;
import edu.classm8web.rs.result.Position;
import edu.classm8web.rs.result.PositionResult;
import edu.classm8web.rs.result.Result;

@Path("position")
public class PositionResource extends AbstractResource {

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPositonsBySchoolclass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("scid") String scid) {
		
		logMessage(this.getClass(), httpServletRequest, "Position by Schoolclass");

		
		PositionResult r = new PositionResult();
		Vector<Position> positions = new Vector<>();
		
		try{
			Long schoolclassId = Long.parseLong(scid);
			
			Schoolclass s = SchoolclassService.getInstance().findById(schoolclassId);
			
			if(s != null){
				
				
				if(!s.getClassMembers().isEmpty()){
					
					
					for(M8 mate : s.getClassMembers()){
						
						Position pos = new Position();
						MappedM8 mapped = ObjectMapper.map(mate);
						pos.setOwner(mapped);
						pos.setCoordinate(mate.getPositionFromSpatial());
						positions.add(pos);
					}
					
					r.setSuccess(true);
					r.setContent(positions);
					
				} else {
					throw new Exception("Class is empty");
				}
			} else {
				throw new Exception("Schoolclass does not exist");
			}
		}catch(Exception e){
			handelAndThrowError(e, r);
		}
		
		return Response.status(Status.ACCEPTED).entity(r).build();
	}

	@POST
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createPosition(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("mid") String mid, final Point p) {
		
		logMessage(this.getClass(), httpServletRequest, "Position by M8 Id");

		
		Result r = new Result();
		
		try {
			Long mateId = Long.parseLong(mid);
			M8 mate = MateService.getInstance().findById(mateId);
			
			if(mate != null && p != null){
				
				mate.insertPositionToSpatial(p);
				
				r.setSuccess(true);
				
			} else {
				throw new Exception("No mate or no point to work with");
			}
			
		} catch (Exception e) {
			handelAndThrowError(e, r);
		}
		
		
		return Response.status(Status.CREATED).entity(r).build();
	}

	@PUT
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePosition(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("mid") String mid, final Point p) {
		
		logMessage(this.getClass(), httpServletRequest, "Position by M8 Id");

		
		Result r = new Result();
		
		try {
			Long mateId = Long.parseLong(mid);
			M8 mate = MateService.getInstance().findById(mateId);
			
			if(mate != null && p != null){
				
				mate.updatePositionToSpatial(p);
				r.setSuccess(true);
				
			} else {
				throw new Exception("No mate or no point to work with");
			}
			
		} catch (Exception e) {
			handelAndThrowError(e, r);
		}
		
		
		return Response.status(Status.ACCEPTED).entity(r).build();
	}
	
	@DELETE
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deletePosition(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("mid") String mid) {
		
		logMessage(this.getClass(), httpServletRequest, "Position by M8 Id");

		
		Result r = new Result();
		
		try {
			Long mateId = Long.parseLong(mid);
			M8 mate = MateService.getInstance().findById(mateId);
			
			if(mate != null){
				
				mate.deletePositionFromSpatial();;
				r.setSuccess(true);
				
			} else {
				throw new Exception("No mate or no point to work with");
			}
			
		} catch (Exception e) {
			handelAndThrowError(e, r);
		}
		
		return Response.status(Status.ACCEPTED).entity(r).build();
	}

}
