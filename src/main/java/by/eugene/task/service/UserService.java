package by.eugene.task.service;

import java.util.List;

import by.eugene.task.model.User;


public interface UserService {
	
	User findById(int id);
	
	User findByUsername(String useres);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByUsername(String useres);

	List<User> findAllUsers(); 
	
	boolean isUserUsernameUnique(Integer id, String useres);

}