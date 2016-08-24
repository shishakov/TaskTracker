package by.eugene.task.model;

import java.io.Serializable;

public enum UserRoleType implements Serializable{
	DEVELOPER("DEVELOPER"),
	MANAGER("MANAGER");
	
	String userRoleType;
	
	private UserRoleType(String userRoleType){
		this.userRoleType = userRoleType;
	}
	
	public String getUserRoleType(){
		return userRoleType;
	}
	
}
