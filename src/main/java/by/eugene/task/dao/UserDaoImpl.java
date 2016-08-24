package by.eugene.task.dao;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import by.eugene.task.model.UserRole;
import by.eugene.task.model.UserRoleType;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import by.eugene.task.model.User;



@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public User findById(int id) {
		User user = getByKey(id);
		if(user!=null){
			Hibernate.initialize(user.getUserRoles());
		}
		return user;
	}

	public User findByUsername(String useres) {
		logger.info("Username : {}", useres);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", useres));
		User user = (User)crit.uniqueResult();
		if(user!=null){
			Hibernate.initialize(user.getUserRoles());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<User> users = (List<User>) criteria.list();
		
		// No need to fetch userRoles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userRoles if you want.
		List<User> usersDev = new ArrayList<>();

		for(User user : users){
			Hibernate.initialize(user.getUserRoles());
			Hibernate.initialize(user.getTaskus());
			for(UserRole userRole: user.getUserRoles()) {
				if ((userRole.getType().compareTo(UserRoleType.MANAGER.getUserRoleType())) != 0) {
					usersDev.add(user);
				}
			}
		}

		return usersDev;
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteByUsername(String useres) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", useres));
		User user = (User)crit.uniqueResult();
		delete(user);
	}

}
