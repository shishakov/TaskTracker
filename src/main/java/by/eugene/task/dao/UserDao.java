package by.eugene.task.dao;

import java.util.List;

import by.eugene.task.model.User;


public interface UserDao {

	User findById(int id);
	
	User findByUsername(String useres);
	
	void save(User user);
	
	void deleteByUsername(String useres);
	
	List<User> findAllUsers();

}

