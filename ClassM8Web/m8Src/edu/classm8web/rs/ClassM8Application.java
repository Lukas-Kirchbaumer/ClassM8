package edu.classm8web.rs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import edu.classm8web.rs.resource.SchoolclassResource;
import edu.classm8web.rs.resource.SecurityResource;
import edu.classm8web.rs.resource.UserResource;

@ApplicationPath("/services")
public class ClassM8Application extends Application{

	public ClassM8Application() {}
	
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<>();
		classes.add(UserResource.class);
		classes.add(SecurityResource.class);
		classes.add(SchoolclassResource.class);
		return classes;
	}
}
