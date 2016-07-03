package bg.uni.sofia.fmi.homeworksystem.email;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import bg.uni.sofia.fmi.homeworksystem.dao.TrainerEmailSchedulerDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.TrainerEmailScheduler;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;

@Interceptor
@RecordUploadSubmission
public class UploadSubmissionInterceptor {
	@Inject
	private TrainerEmailSchedulerDAO trainerEmailSchedulerDAO;
	
	@AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        UploadedSubmission uploadedSubmission = (UploadedSubmission) parameters[0];
        
        Course course = uploadedSubmission.getLecture().getCourse();
        if (course.isFavouriteToTrainer()){
        	Trainee trainee = uploadedSubmission.getTrainee();
        	Lecture lecture = uploadedSubmission.getLecture();
        	TrainerEmailScheduler trainerEmailScheduler = new TrainerEmailScheduler(trainee, lecture);
        	trainerEmailSchedulerDAO.save(trainerEmailScheduler);
        }
        return ctx.proceed();
    }
}
