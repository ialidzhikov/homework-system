package bg.uni.sofia.fmi.homeworksystem.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;

public class SendMailsJob extends AbstractJob<User>{	
	private final Logger LOGGER = LoggerFactory.getLogger(SendMailsJob.class);
	
	public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
		LOGGER.error("RABOTIM");
	}
}
