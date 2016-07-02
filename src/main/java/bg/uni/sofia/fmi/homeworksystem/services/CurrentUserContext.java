package bg.uni.sofia.fmi.homeworksystem.services;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.utils.UserNullException;

@SessionScoped
public class CurrentUserContext implements Serializable{
	private static final long serialVersionUID = -6225458652154987336L;
	private static final String USER_NULL_MESSAGE = "User can not be null.";
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if(user == null) {
			throw new UserNullException(USER_NULL_MESSAGE);
		}
		
		this.user = user;
	}
}
