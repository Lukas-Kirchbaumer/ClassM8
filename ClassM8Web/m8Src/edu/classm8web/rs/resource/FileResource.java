package edu.classm8web.rs.resource;

import java.io.FileInputStream;
import java.io.InputStream;
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
import edu.classm8web.database.dto.File;
import edu.classm8web.database.dto.Schoolclass;
import edu.classm8web.rs.result.Result;

@Path("file")
public class FileResource extends AbstractResource {

	
	
	@GET
	@Path("content/{fileid}")
	@Produces(value = { MediaType.APPLICATION_OCTET_STREAM })
	public Response streamFileById(@PathParam("fileid") String fileid) {

		Result r = new Result();

		ResponseBuilder builder = null;

		try {
			File file = FileService.getInstance().findById(Long.valueOf(fileid));

			java.io.File f = new java.io.File(
					"D:\\servers\\glassfish4\\glassfish\\domains\\cm8\\generated\\data\\" + file.getFileName());

			InputStream is = new FileInputStream(f);

			builder = Response.status(Status.ACCEPTED).entity(is);
			builder.type(file.getContentType());
			builder.header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");

		} catch (Exception e) {
			handelAndThrowError(e, r);
		}

		return builder.build();
	}

//	@POST
//	@Path("content/{fileid}")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
//			@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("fileid") String fileid) {
//		return null;
//	}

	@POST
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes("application/json")
	public Response createFileMetaDataInGroup(@QueryParam("schoolclassid") String schoolclassid, final File input) {

		Result r = new Result();

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
