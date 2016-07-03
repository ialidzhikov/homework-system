package bg.uni.sofia.fmi.homeworksystem.job;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.uni.sofia.fmi.homeworksystem.cdi.EntityManagerProducer;

public abstract class AbstractJob<T> implements Job{
	private final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);
	protected EntityManagerProducer emp = new EntityManagerProducer();
	protected final EntityManager em = emp.getEntityManager();
}
