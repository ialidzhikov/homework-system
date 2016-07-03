package bg.uni.sofia.fmi.homeworksystem.scheduler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.uni.sofia.fmi.homeworksystem.contracts.JobScheduler;
import bg.uni.sofia.fmi.homeworksystem.job.SendMailsJob;

@ApplicationScoped
public class JobSchedulerImpl implements JobScheduler{
	private Scheduler scheduler;
	private static final int SCHEDULE_HOUR = 18;
	private static final int SCHEDULE_MINUTE = 00;
	 
   @Inject
   private CdiJobFactory cdiJobFactory;
 
   private final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerImpl.class);
 
   /**
    * Schedules and starts jobs. This method is automatically called when the application starts.
    * @param init it is used for automatic call of this method.
    */
   public void scheduleJobs(@Observes @Initialized(ApplicationScoped.class) Object init) {
       try {
          scheduler = new StdSchedulerFactory().getScheduler();
          scheduler.setJobFactory(cdiJobFactory);
          schedule(SendMailsJob.class, "createOrders", SCHEDULE_HOUR, SCHEDULE_MINUTE);
          scheduler.start(); 
       } catch (SchedulerException e) {
          LOGGER.error("Unable to start scheduler", e);
       }
    }
	
    /**
     * Stops all the jobs.This method is automatically called before application stop.
     * @param init it is used for automatic call of this method.
     */
	public void stopJobs(@Observes @Destroyed(ApplicationScoped.class) Object init) {
		if(scheduler != null) {
	        try {
	        	scheduler.shutdown(true);
	        } catch (SchedulerException e) {
	           LOGGER.error("Unable to stop scheduler", e);
            }
	    }
    }
	
	/**
	 * Schedules the jobs with monthly repeating trigger.
	 * @param jobClass the class that will be invoked.
	 * @param jobIdentity identity of the job.
	 * @param day the day which the job will be started.
	 * @param hour the hour which the job will be started.
	 * @param minute the minute which the job will be started.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void schedule(Class jobClass, String jobIdentity, int hour, int minute) throws SchedulerException {
		final JobDetail detail = JobBuilder.newJob(jobClass).withIdentity(jobIdentity, jobIdentity + "-job").build();
		final CronTrigger trigger = TriggerBuilder.newTrigger()
		           .startNow()
		           .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(hour, minute))
		           .build();
		scheduler.scheduleJob(detail, trigger);
	}
}
