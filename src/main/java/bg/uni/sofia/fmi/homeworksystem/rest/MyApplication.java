package bg.uni.sofia.fmi.homeworksystem.rest;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
   public MyApplication() {
	   register(MOXyJsonProvider.class);
	   packages("org.glassfish.jersey.examples.multipart").register(MultiPartFeature.class);
   }
}