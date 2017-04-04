package edu.classm8web.rs;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;



import edu.classm8web.database.dao.FileService;
import edu.classm8web.database.dao.MateService;
import edu.classm8web.database.dao.SchoolclassService;
import edu.classm8web.rs.resource.ElectionResource;
import edu.classm8web.rs.resource.EmoteResource;
import edu.classm8web.rs.resource.FileResource;
import edu.classm8web.rs.resource.PositionResource;
import edu.classm8web.rs.resource.SchoolclassResource;
import edu.classm8web.rs.resource.SecurityResource;
import edu.classm8web.rs.resource.UserResource;
import edu.classm8web.database.dao.SpatialService;

@ApplicationPath("/services")
public class ClassM8Application extends Application{

	public ClassM8Application() {}
	
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<>();
		classes.add(UserResource.class);
		classes.add(SecurityResource.class);
		classes.add(SchoolclassResource.class);
		classes.add(FileResource.class);
		classes.add(EmoteResource.class);
		classes.add(ElectionResource.class);
		classes.add(PositionResource.class);
		classes.add(MultiPartFeature.class);
		return classes;
	}
	
	
	@PostConstruct
	private void servletStartUp(){
		System.out.println("######## START - JAX RS - SERVLET ########");
		MateService.getInstance();
		SchoolclassService.getInstance();
		FileService.getInstance(); //Create Persistence Components
		
		String cs = FileService.getInstance().getConnectionSting(); //Read DB Credentials from 
		String user = FileService.getInstance().getUsername();
		String pw = FileService.getInstance().getPassword();
		
		System.out.println("Read DB creds from EM: " + cs + " / " + user + " / " + pw);
		SpatialService.getInstance().createConnection(cs, user, pw);

		
		System.out.println(MateService.getInstance().getEm());
		System.out.println(SchoolclassService.getInstance().getEm());
		System.out.println(FileService.getInstance().getEm());
		


	}
	
	@PreDestroy
	private void servletTearDown(){
		if(MateService.getInstance().getEm().isOpen()){
			MateService.getInstance().closeEntityManager();
			MateService.getInstance().closeEntityManagerFactory();
		}
		
		if(SchoolclassService.getInstance().getEm().isOpen()){
			SchoolclassService.getInstance().closeEntityManager();
			SchoolclassService.getInstance().closeEntityManagerFactory();
		}
		
		if(FileService.getInstance().getEm().isOpen()){
			FileService.getInstance().closeEntityManager();
			FileService.getInstance().closeEntityManagerFactory();
		}

		System.out.println("######## STOP - JAX RS - SERVLET ########");

	}
}
