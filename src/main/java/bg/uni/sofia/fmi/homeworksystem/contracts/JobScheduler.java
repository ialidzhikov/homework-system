package bg.uni.sofia.fmi.homeworksystem.contracts;

public interface JobScheduler {
	void scheduleJobs(Object init);
	void stopJobs(Object init);
}
