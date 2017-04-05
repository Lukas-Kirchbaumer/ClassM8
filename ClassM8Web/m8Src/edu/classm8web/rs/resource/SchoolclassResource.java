package edu.classm8web.rs.resource;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Vector;

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
import javax.ws.rs.core.Response.Status;

import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dao.SchoolclassService;
import edu.classm8web.database.dto.Chat;
import edu.classm8web.database.dto.M8;
import edu.classm8web.database.dto.Message;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.mapper.ObjectMapper;
import edu.classm8web.mapper.objects.MappedSchoolclass;
import edu.classm8web.rs.result.ChatResult;
import edu.classm8web.rs.result.Result;
import edu.classm8web.rs.result.SchoolclassResult;

@Path("schoolclass")
public class SchoolclassResource extends AbstractResource {

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllSchoolclasses(@Context Request request, @Context HttpServletRequest httpServletRequest) {

		logMessage(this.getClass(), httpServletRequest, "All schoolclasses");

		workaround();

		SchoolclassResult res = new SchoolclassResult();

		try {
			Vector<MappedSchoolclass> resObject = new Vector<>();
			resObject.addAll(ObjectMapper.mapSchoolclasses(SchoolclassService.getInstance().findAll()));
			res.setSchoolclasses(resObject);
			res.setSuccess(true);
		} catch (Exception e) {
			handelAndThrowError(e, res);
		}
		return Response.status(Response.Status.ACCEPTED).entity(res).build();
	}

	@PUT
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateSchoolclass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id, final Schoolclass input) {

		logMessage(this.getClass(), httpServletRequest, "Update schoolclass");

		Result r = new Result();

		try {
			Schoolclass sc = SchoolclassService.getInstance().findById(Long.parseLong(id));
			sc.setNewClass(input);
			SchoolclassService.getInstance().update(sc);
			r.setSuccess(true);

		} catch (Exception e) {
			handelAndThrowError(e, r);
		}
		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@POST
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createSchoolclass(@Context HttpServletRequest httpServletRequest, final Schoolclass input,
			@QueryParam("m8id") String id) {

		logMessage(this.getClass(), httpServletRequest, "Create schoolclass");

		Result r = new Result();

		try {
			M8 m8 = MateService.getInstance().findById(Long.parseLong(id));
			if (m8 != null) {
				SchoolclassService.getInstance().persist(input);
				input.getClassMembers().add(m8);
				m8.setSchoolclass(input);

				MateService.getInstance().update(m8);
				SchoolclassService.getInstance().update(input);

				Chat c = new Chat();
				List<Message> message = new ArrayList<>();

				c.setMessages(message);

				input.setSchoolclassChat(c);
				SchoolclassService.getInstance().update(input);
				
				input.setCustomEmojis();
				SchoolclassService.getInstance().update(input);

				r.setSuccess(true);
			} else {
				throw new Exception("M8 not persisted");
			}

		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@DELETE
	@Consumes("application/json")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteSchoolclass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("id") String id) {

		logMessage(this.getClass(), httpServletRequest, "Delete schoolclass");

		Result r = new Result();

		try {
			SchoolclassService.getInstance().removeById(Long.parseLong(id));
			r.setSuccess(true);

		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@GET
	@Path("{m8id}")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getClassbyUser(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@PathParam("m8id") String id) {

		logMessage(this.getClass(), httpServletRequest, "Return class for m8");

		workaround();

		SchoolclassResult res = new SchoolclassResult();

		try {
			M8 m8 = MateService.getInstance().findById(Long.parseLong(id));
			if (m8 != null) {
				if (m8.getSchoolclass() != null) {
					Vector<MappedSchoolclass> msc = new Vector<MappedSchoolclass>();
					msc.add(ObjectMapper.map(m8.getSchoolclass()));
					res.setSchoolclasses(msc);
					res.setSuccess(true);
				}
			}
		} catch (Exception e) {
			handelAndThrowError(e, res);
		}

		return Response.status(Response.Status.ACCEPTED).entity(res).build();
	}

	@POST
	@Path("{m8id}")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addM8ToSchoolClass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@PathParam("m8id") String m8id, @QueryParam("scid") String scid) {

		logMessage(this.getClass(), httpServletRequest, "Add m8 to schoolclass");

		Result r = new Result();

		try {

			M8 m8 = MateService.getInstance().findById(Long.parseLong(m8id));
			Schoolclass sc = SchoolclassService.getInstance().findById(Long.parseLong(scid));

			if (m8 != null && sc != null) {
				sc.getClassMembers().add(m8);
				m8.setSchoolclass(sc);
				SchoolclassService.getInstance().update(sc);
				MateService.getInstance().update(m8);
				r.setSuccess(true);
			} else {
				throw new Exception("m8 or schoolclass not in database");
			}

		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@DELETE
	@Path("{m8id}")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response removeM8fromSchoolClass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@PathParam("m8id") String m8id, @QueryParam("scid") String scid) {

		logMessage(this.getClass(), httpServletRequest, "Remove m8 from schoolclass");

		Result r = new Result();

		try {

			M8 m8 = MateService.getInstance().findById(Long.parseLong(m8id));
			Schoolclass sc = SchoolclassService.getInstance().findById(Long.parseLong(scid));

			if (m8 != null && sc != null) {
				int i = 0;
				int toremove = -1;
				for (M8 m88 : sc.getClassMembers()) {
					if (m88.getId() == m8.getId()) {
						toremove = i;
					}
					i++;
				}
				if (toremove != -1) {
					sc.getClassMembers().remove(toremove);
					SchoolclassService.getInstance().update(sc);
					MateService.getInstance().update(m8);
					r.setSuccess(true);
				} else {
					throw new Exception("M8 not in schoolclass");
				}
			} else {
				throw new Exception("M8 or Schoolclass not in database");
			}

		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Response.Status.ACCEPTED).entity(r).build();
	}

	@GET
	@Path("chat")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChatFromSchoolclass(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("scid") String id, @QueryParam("limit") String limit) {

		logMessage(this.getClass(), httpServletRequest, "Chat content");
		ChatResult result = new ChatResult();
		Chat mapped = new Chat();
		Timestamp limitDate = null;
		try{
			limitDate = Timestamp.valueOf(limit);
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Warning: No Date");
			limitDate = null;
		}

		try {
			Schoolclass sc = SchoolclassService.getInstance().findById(Long.parseLong(id));

			if (sc != null && limitDate != null) {
				Chat c = sc.getSchoolclassChat();

				if (c != null) {
					List<Message> mes = c.getMessagesAfterDate(limitDate);
					
					if(mes.size() != 0){
						mapped.setMessages(mes);
						Collections.sort(mapped.getMessages());
						mapped.setId(c.getId());
						result.setSchoolclassChat(mapped);
						result.setSuccess(true);
					}
				}
			} else {
				throw new Exception("Schoolclass not found");
			}
		}catch (Exception e) {
			handelAndThrowError(e, result);
		}


		return Response.status(Status.ACCEPTED).entity(result).build();
	}

	@POST
	@Path("chat")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response pushMessage(@Context Request request, @Context HttpServletRequest httpServletRequest,
			@QueryParam("scid") String scid, @QueryParam("m8id") String m8id, final String message) {

		logMessage(this.getClass(), httpServletRequest, "Push message");

		Result r = new Result();

		try {
			Long mid = Long.parseLong(m8id);
			Long sid = Long.parseLong(scid);

			M8 mate = MateService.getInstance().findById(mid);
			Schoolclass schoolclass = SchoolclassService.getInstance().findById(sid);

			if (mate != null && schoolclass != null) {
				Message m = new Message();
				m.setSender(mate.getFirstname() + " " + mate.getLastname());
				m.setContent(message);

				Date now = new Date();
				Timestamp t = new Timestamp(now.getTime());
				m.setDateTime(t);

				schoolclass.getSchoolclassChat().getMessages().add(m);
				SchoolclassService.getInstance().update(schoolclass);

				r.setSuccess(true);

			} else {
				throw new Exception("Mate or schoolclass does not exist");
			}
		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Status.CREATED).entity(r).build();
	}
}
