package bg.uni.sofia.fmi.homeworksystem.utils;

public class UserNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UserNullException(String message) {
        super(message);
    }
}
