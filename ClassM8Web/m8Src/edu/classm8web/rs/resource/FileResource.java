package edu.classm8web.rs.resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import edu.classm8web.database.dao.FileService;
import edu.classm8web.database.dao.SchoolclassService;
import edu.classm8web.database.dto.Emote;
import edu.classm8web.database.dto.File;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.rs.result.LoginResult;
import edu.classm8web.rs.result.Result;

@Path("file")
public class FileResource extends AbstractResource {

	private static final String DATA_PATH = "E:\\HTL\\BSD\\5. Klasse\\Glassfish\\glassfish4\\glassfish\\domains\\cm8\\generated\\data\\";
	
	
	@GET
	@Path("content/{fileid}")
	@Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
	public Response streamFileById(@PathParam("fileid") String fileid) {

		Result r = new Result();

		ResponseBuilder builder = null;

		System.out.println(fileid);
		try {
			File file = FileService.getInstance().findById(Long.valueOf(fileid));
			if(file != null){
				java.io.File f = new java.io.File(
						DATA_PATH + file.getFileName());
	
				InputStream is = new FileInputStream(f);
	
				builder = Response.status(Status.ACCEPTED).entity(is);
				builder.type(file.getContentType());
				builder.header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
				r.setSuccess(true);
			}

		} catch (Exception e) {
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
			File f = FileService.getInstance().findById(Long.parseLong(fileid));
			if(f != null){
		        OutputStream os = null;
		        java.io.File fileToUpload = new java.io.File(DATA_PATH + f.getFileName());
		        fileToUpload.getParentFile().mkdirs(); 
		        fileToUpload.createNewFile();
		        os = new FileOutputStream(fileToUpload);
		        byte[] b = new byte[(int) f.getContentSize()];
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
		return null;
	}

	@POST
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes("application/json")
	public Response createFileMetaDataInGroup(@QueryParam("schoolclassid") String schoolclassid, final File input) {

		LoginResult r = new LoginResult();

		try {
			Schoolclass s = SchoolclassService.getInstance().findById(Long.valueOf(schoolclassid));
			if (s != null) {
				Date now = new Date();
				input.setUploadDate(new java.sql.Date(now.getTime()));
				FileService.getInstance().persist(input);

				s.getFiles().add(input);
				input.setReferencedSchoolclass(s);

				SchoolclassService.getInstance().update(s);
				FileService.getInstance().update(input);

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
	
	
	
}
