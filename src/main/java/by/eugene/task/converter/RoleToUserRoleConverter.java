package by.eugene.task.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import by.eugene.task.model.UserRole;
import by.eugene.task.service.UserRoleService;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class RoleToUserRoleConverter implements Converter<Object, UserRole>{

	static final Logger logger = LoggerFactory.getLogger(RoleToUserRoleConverter.class);
	
	@Autowired
	UserRoleService userProfileService;

	/**
	 * Gets UserRole by Id
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public UserRole convert(Object element) {
		Integer id = Integer.parseInt((String)element);
		UserRole profile= userProfileService.findById(id);
		logger.info("Profile : {}",profile);
		return profile;
	}
	
}