package by.eugene.task.dao;

import java.util.List;

import by.eugene.task.model.UserRole;


public interface UserRoleDao {

	List<UserRole> findAll();
	
	UserRole findByType(String type);
	
	UserRole findById(int id);
}
