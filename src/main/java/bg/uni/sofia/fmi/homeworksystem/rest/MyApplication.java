package bg.uni.sofia.fmi.homeworksystem.rest;

import javax.json.stream.JsonGenerator;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import bg.uni.sofia.fmi.homeworksystem.services.CourseService;
import bg.uni.sofia.fmi.homeworksystem.services.HomeService;
import bg.uni.sofia.fmi.homeworksystem.services.SubmissionService;
import bg.uni.sofia.fmi.homeworksystem.services.TraineeService;
import bg.uni.sofia.fmi.homeworksystem.services.TrainerService;

public class MyApplication extends ResourceConfig {
   public MyApplication() {
	   register(HomeService.class);
	   register(CourseService.class);
	   register(TraineeService.class);
	   register(SubmissionService.class);
	   register(TrainerService.class);
	   register(MOXyJsonProvider.class);
	   register(JsonProcessingFeature.class);
	   packages("org.glassfish.jersey.examples.jsonp.resource");
       property(JsonGenerator.PRETTY_PRINTING, true);
	   packages("org.glassfish.jersey.examples.multipart").register(MultiPartFeature.class);
   }
}