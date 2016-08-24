package by.eugene.task.service;

import java.util.List;

import by.eugene.task.model.UserRole;


public interface UserRoleService {

	UserRole findById(int id);

	UserRole findByType(String type);
	
	List<UserRole> findAll();
	
}
