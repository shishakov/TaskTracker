package by.eugene.task.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.eugene.task.dao.TaskDao;
import by.eugene.task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.eugene.task.dao.UserDao;
import by.eugene.task.model.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findByUsername(String useres) {
		User user = dao.findByUsername(useres);
		return user;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			entity.setUsername(user.getUsername());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setUserRoles(user.getUserRoles());
			entity.setTaskus(user.getTaskus());
		}
	}

	
	public void deleteUserByUsername(String useres) {

		List<Task> tasks = taskDao.findAllTasks();
		Set<User> userSet = new HashSet<>();
		User user = dao.findByUsername(useres);
		for(Task task: tasks){
			for(User userTask:task.getUsers()){
				if(userTask.hashCode()==user.hashCode()){
					userSet = task.getUsers();
					userSet.remove(userTask);
					task.setUsers(userSet);
					taskDao.save(task);
				}
			}
		}
		dao.deleteByUsername(useres);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public boolean isUserUsernameUnique(Integer id, String useres) {
		User user = findByUsername(useres);
		return ( user == null || ((id != null) && (user.getId() == id)));
	}
	
}
