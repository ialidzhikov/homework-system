package bg.uni.sofia.fmi.homeworksystem.email;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;

@Interceptor
@RecordMarks
public class EvaluationInterceptor {
	
	@AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        UploadedSubmission uploadedSubmission = (UploadedSubmission) parameters[0];
        Double mark = (Double) parameters[1];
        String email = uploadedSubmission.getTrainee().getEmail();
        String body = "Your submission: " + uploadedSubmission.getLecture().getTask() + " is evaluated with " + mark + ".";
		EmailSender.sendEmail(email, "Homerk system", body);
        return ctx.proceed();
    }
}
