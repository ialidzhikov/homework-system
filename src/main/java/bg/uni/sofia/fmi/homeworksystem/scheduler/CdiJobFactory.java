package bg.uni.sofia.fmi.homeworksystem.scheduler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CdiJobFactory implements JobFactory {
 
   @Inject
   private BeanManager bm;
	
   private final Logger LOGGER = LoggerFactory.getLogger(CdiJobFactory.class);
 
   public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler) throws SchedulerException {
 
      final JobDetail jobDetail = bundle.getJobDetail();
      final Class<? extends Job> jobClass = jobDetail.getJobClass();
      try {
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                  "Producing instance of Job '" + jobDetail.getKey() +
                        "', class=" + jobClass.getName());
         }
 
         return getBean(jobClass);
      } catch (Exception e) {
         throw new SchedulerException(
               "Problem instantiating class '"
                     + jobDetail.getJobClass().getName() + "'", e);
      }
   }
 
   private Job getBean(Class jobClass) {
      final Bean<?> bean =  bm.getBeans(jobClass).iterator().next();
      final CreationalContext<?> ctx = bm.createCreationalContext(bean);
      return (Job) bm.getReference(bean, jobClass, ctx);
   }
}
