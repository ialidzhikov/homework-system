package bg.uni.sofia.fmi.homeworksystem.services;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;

@SessionScoped
public class CurrentUserContext implements Serializable{
	private static final long serialVersionUID = -6225458652154987336L;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
