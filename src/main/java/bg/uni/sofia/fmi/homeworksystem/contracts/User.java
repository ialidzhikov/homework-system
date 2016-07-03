package bg.uni.sofia.fmi.homeworksystem.contracts;

import bg.uni.sofia.fmi.homeworksystem.utils.Role;

public interface User {
	Role getUserRole();
	String getUsername();
}
