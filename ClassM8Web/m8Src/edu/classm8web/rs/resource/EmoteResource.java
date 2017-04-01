package edu.classm8web.rs.resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import edu.classm8web.database.dao.EmoteService;
import edu.classm8web.database.dao.FileService;
import edu.classm8web.database.dao.SchoolclassService;
import edu.classm8web.database.dto.Emote;
import edu.classm8web.database.dto.File;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.mapper.ObjectMapper;
import edu.classm8web.rs.result.EmoteResult;
import edu.classm8web.rs.result.LoginResult;
import edu.classm8web.rs.result.Result;

@Path("emote")
public class EmoteResource extends AbstractResource {
	

	private static final String EMOTE_PATH = "E:\\HTL\\BSD\\5. Klasse\\Glassfish\\glassfish4\\glassfish\\domains\\cm8\\generated\\emotes\\";
	
	
	@GET
	@Path("content/{fileid}")
	@Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
	public Response streamFileById(@Context HttpServletRequest httpServletRequest, @PathParam("fileid") String fileid) {

		logMessage(this.getClass(), httpServletRequest, "Stream binary file");
		
		Result r = new Result();

		ResponseBuilder builder = null;

		try {
			File file = FileService.getInstance().findById(Long.valueOf(fileid));
			if(file != null){
				java.io.File f = new java.io.File(
						EMOTE_PATH + file.getFileName());
	
				InputStream is = new FileInputStream(f);
	
				builder = Response.status(Status.ACCEPTED).entity(is);
				builder.type(file.getContentType());
				builder.header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
				r.setSuccess(true);
			}

		} catch (Exception e) {
			System.out.println("bört");
			handelAndThrowError(e, r);
		}


		return builder.build();
	}

	
	@POST
	@Path("content/{fileid}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
	@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("fileid") String fileid) {
		Result r = new Result();
		try{
			Emote e = EmoteService.getInstance().findById(Long.parseLong(fileid));
			if(e != null){
		        OutputStream os = null;
		        java.io.File fileToUpload = new java.io.File(EMOTE_PATH + e.getFileName());
		        fileToUpload.getParentFile().mkdirs(); 
		        fileToUpload.createNewFile();
		        os = new FileOutputStream(fileToUpload);
		        byte[] b = new byte[(int) e.getContentSize()];
		        int length;
		        while ((length = uploadedInputStream.read(b)) != -1) {
		            os.write(b, 0, length);
		        }
		        os.flush();
		        os.close();
			}
		}
		catch(Exception e){
			handelAndThrowError(e, r);
		}
		return Response.status(Status.ACCEPTED).entity(r).build();
	}
	
	
	
	
	@POST
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes("application/json")
	public Response createEmoteMetaDataInGroup(@QueryParam("schoolclassid") String schoolclassid, final Emote input) {

		LoginResult r = new LoginResult();

		System.out.println(schoolclassid);
		
		try {
			Schoolclass s = SchoolclassService.getInstance().findById(Long.valueOf(schoolclassid));
			if (s != null) {
				input.setReferencedSchoolclass(s);
				EmoteService.getInstance().persist(input);

				s.getEmotes().add(input);
				input.setReferencedSchoolclass(s);
				SchoolclassService.getInstance().update(s);

				r.setId(input.getId());
				r.setSuccess(true);

			} else {
				throw new Exception("Schoolclass doesn't exist");
			}
		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return Response.status(Status.ACCEPTED).entity(r).build();
	}
	
	
	@GET
	@Path("all/{scid}")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllEmotesForSchoolClass(@PathParam("scid") String scid) {

		System.out.println("fgt1");
		EmoteResult r = new EmoteResult();
		System.out.println("fgt2");
		try{
			System.out.println("fgt3");
			r.setEmotes(ObjectMapper.map(SchoolclassService.getInstance().findById(Long.valueOf(scid)).getEmotes()));
			r.setSuccess(true);
			System.out.println("fgt4");
		}
		catch(Exception e){
			handelAndThrowError(e, r);
		}
		System.out.println("fgt5");
		return Response.status(Status.ACCEPTED).entity(r).build();
	}
}
