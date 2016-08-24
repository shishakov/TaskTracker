package by.eugene.task.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import by.eugene.task.model.*;
import by.eugene.task.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	UserService userService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@Autowired
	CommentService commentService;
	
	@Autowired
	UserRoleService userProfileService;

	@Autowired
	TaskStatusService taskStatusService;
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	
	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		User userDev = userService.findByUsername(getPrincipal());
		Set<UserRole> userRoles = userDev.getUserRoles();
		List<Project> projects;
		for(UserRole userRole: userRoles){
			if((userRole.getType().compareTo("DEVELOPER"))==0){
				projects = new ArrayList<>(projectService.findAllProjectUser(userDev.getUsername()));
				model.addAttribute("projects", projects);
			}else {
				projects = new ArrayList<>();
				projects = projectService.findAllProjects();
				model.addAttribute("projects", projects);
			}
		}
		List<User> users = userService.findAllUsers();
		List<Task> tasks= taskService.findAllTasks();
		List<Comment> comments = commentService.findAllComments();
		model.addAttribute("users", users);
		model.addAttribute("tasks", tasks);
		model.addAttribute("comments", comments);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userslist";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newproject" }, method = RequestMethod.GET)
	public String newProject(ModelMap model) {
		Project project = new Project();
		model.addAttribute("project", project);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addproject";
	}

	@RequestMapping(value = { "/newtask" }, method = RequestMethod.GET)
	public String newTask(ModelMap model) {
		Task task = new Task();
		model.addAttribute("task", task);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addtask";
	}

	@RequestMapping(value = { "/newcomment-{description}" }, method = RequestMethod.GET)
	public String newComment(@PathVariable String description, ModelMap model) {
		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addcomment";
	}

	@RequestMapping(value = { "/add-project-task-dev" }, method = RequestMethod.GET)
	public String projectTaskDev(ModelMap model) {
		List<Project> projects = projectService.findAllProjects();
		model.addAttribute("projects", projects);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "projectTaskDev";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [useres] should be implementing custom @Unique annotation 
		 * and applying it on field [useres] of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
		if(!userService.isUserUsernameUnique(user.getId(), user.getUsername())){
			FieldError useresError =new FieldError("user","username",messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
		    result.addError(useresError);
			return "registration";
		}
		
		userService.saveUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		//return "success";
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/newproject" }, method = RequestMethod.POST)
	public String saveProject(@Valid Project project, BindingResult result,
						   ModelMap model) {

		if (result.hasErrors()) {
			return "addproject";
		}

		projectService.saveProjectTask(project);

		model.addAttribute("success", "Project " + project.getNameProject() + " added successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddproject";
	}

	@RequestMapping(value = { "/newtask" }, method = RequestMethod.POST)
	public String saveTask(@Valid Task task, BindingResult result,
							  ModelMap model) {

		if (result.hasErrors()) {
			return "addtask";
		}

		taskService.saveTask(task);

		model.addAttribute("success", "Task " + task.getDescription() + " added successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddtask";
	}

	@RequestMapping(value = { "/newcomment-{description}" }, method = RequestMethod.POST)
	public String saveComment(@Valid Comment comment, BindingResult result,
						   ModelMap model, @PathVariable String description) {

		if (result.hasErrors()) {
			return "addcomment";
		}

		Task task = taskService.findByDescription(description);

		Set<Comment> commentList = new HashSet<>();
		commentList = task.getTaskSet();
		commentList.add(comment);
		task.setTaskSet(commentList);

		taskService.updateTask(task);

		model.addAttribute("success", "Comment " + comment.getContentComment() + " added successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddcomment";
	}



	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{username}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String username, ModelMap model) {
		User user = userService.findByUsername(username);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	@RequestMapping(value = { "/project-task-dev2-{nameProject}" }, method = RequestMethod.GET)
	public String projectTaskDev2(@PathVariable String nameProject, ModelMap model) {
		List<Task>  tasks = projectService.findAllTaskProject(nameProject);
		Project project = projectService.findByNameProject(nameProject);
		model.addAttribute("tasks", tasks);
		model.addAttribute("project", project);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "projectTaskDev2";
	}

	@RequestMapping(value = { "/project-task-dev3-{nameProject}-{taskName}" }, method = RequestMethod.GET)
	public String projectTaskDev3(@PathVariable String nameProject, @PathVariable String taskName, ModelMap model) {
		Project project = projectService.findByNameProject(nameProject);
		Task task = taskService.findByDescription(taskName);
		List<User> users = userService.findAllUsers();
		model.addAttribute("task", task);
		model.addAttribute("project", project);
		model.addAttribute("users",users);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "projectTaskDev3";
	}

	@Transactional
	@RequestMapping(value = { "/project-task-dev4-{taskdescription}-{username}" }, method = RequestMethod.GET)
	public String projectTaskDev4(@PathVariable String taskdescription, @PathVariable String username, ModelMap model) {

		User user = userService.findByUsername(username);
		Task task = taskService.findByDescription(taskdescription);
		Set<Task> taskList = new HashSet<>();

		taskList = user.getTaskus();
		taskList.add(task);
		user.setTaskus(taskList);

		userService.updateUser(user);

		model.addAttribute("success", "In task " + task.getDescription() + " added developer " + user.getEmail() + " successfully!!");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddproject";
	}

	/**
	 * This method will provide the medium to update an existing project.
	 */
	@RequestMapping(value = { "/edit-project-{projectname}" }, method = RequestMethod.GET)
	public String editProject(@PathVariable String projectname, ModelMap model) {
		Project project = projectService.findByNameProject(projectname);
		model.addAttribute("project", project);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addproject";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{username}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String username) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
		if(!userService.isUserUsernameUnique(user.getId(), user.getUsername())){
			FieldError useresError =new FieldError("user","username",messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
		    result.addError(useresError);
			return "registration";
		}*/


		userService.updateUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/edit-task-{description}" }, method = RequestMethod.GET)
	public String updateTask(@PathVariable String description, ModelMap model) {
		Task task = taskService.findByDescription(description);
		model.addAttribute("task", task);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addtask";
	}

	@RequestMapping(value = { "/edit-comment-{content}" }, method = RequestMethod.GET)
	public String updateComment(@PathVariable String content, ModelMap model) {
		Comment comment = commentService.findByContentComment(content);
		model.addAttribute("comment", comment);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addcomment";
	}

	@RequestMapping(value = { "/edit-project-{projectname}" }, method = RequestMethod.POST)
	public String updateProject(@Valid Project project, BindingResult result,
							 ModelMap model, @PathVariable String projectname) {

		if (result.hasErrors()) {
			return "addproject";
		}

		projectService.updateProject(project);

		model.addAttribute("success", "Project " + project.getNameProject() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddproject";
	}



	@RequestMapping(value = { "/edit-task-{description}" }, method = RequestMethod.POST)
	public String updateTask(@Valid Task task, BindingResult result,
								ModelMap model, @PathVariable String description) {

		if (result.hasErrors()) {
			return "addtask";
		}

		taskService.updateTask(task);

		model.addAttribute("success", "Task " + task.getDescription() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddtask";
	}

	@RequestMapping(value = { "/edit-comment-{content}" }, method = RequestMethod.POST)
	public String updateComment(@Valid Comment comment, BindingResult result,
							 ModelMap model, @PathVariable String content) {

		if (result.hasErrors()) {
			return "addcomment";
		}

		commentService.updateComment(comment);

		model.addAttribute("success", "Comment " + comment.getContentComment() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddcomment";
	}

	@RequestMapping(value = { "/add-projecttask-{projectname}" }, method = RequestMethod.GET)
	public String addProjectTask(@PathVariable String projectname, ModelMap model) {

		Project project = projectService.findByNameProject(projectname);
		Task task = new Task();
		model.addAttribute("task", task);
		model.addAttribute("project", project);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addprojecttask";
	}

	@RequestMapping(value = { "/edittaskstatus-{description}" }, method = RequestMethod.GET)
	public String editTaskStatus(@PathVariable String description, ModelMap model) {
		Task task = taskService.findByDescription(description);
		model.addAttribute("task", task);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "changeStatusTask";
	}

	@RequestMapping(value = { "/show-task-{projectname}" }, method = RequestMethod.GET)
	public String addDevProjectTask(@PathVariable String projectname, ModelMap model) {

		Project project = projectService.findByNameProject(projectname);
		List<Task> tasks = taskService.findAllTasksProject(projectname);
		Task task = new Task();
		model.addAttribute("task", task);
		model.addAttribute("tasks",tasks);
		model.addAttribute("project", project);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addProjectTaskDev";
	}

	@RequestMapping(value = { "/show-{nameProject}-{description}" }, method = RequestMethod.GET)
	public String showTaskComment(@PathVariable String nameProject, @PathVariable String description, ModelMap model) {

		Project project = projectService.findByNameProject(nameProject);
		Task task = taskService.findByDescription(description);
		List<Comment> comments = taskService.findAllTaskComments(description);
		Comment comment = new Comment();

		model.addAttribute("task", task);
		model.addAttribute("project", project);
		model.addAttribute("comment", comment);
		model.addAttribute("comments", comments);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "listCommentTask";
	}

	@Transactional
	@RequestMapping(value = { "/add-projecttask-{projectname}" }, method = RequestMethod.POST)
	public String addProjectTask(@Valid Task task, BindingResult result,
							 ModelMap model, @PathVariable String projectname) {

		if (result.hasErrors()) {
			return "addprojecttask";
		}

		Project project = projectService.findByNameProject(projectname);
		Set<Task> taskList = new HashSet<>();
		taskList = project.getTasks();
		taskList.add(task);
		project.setTasks(taskList);

		projectService.updateProject(project);

		model.addAttribute("success", "In project " + project.getNameProject() + " added task " + task.getDescription() + " successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddproject";
	}

	@Transactional
	@RequestMapping(value = { "/edittaskstatus-{description}" }, method = RequestMethod.POST)
	public String editTaskStatus(@Valid Task task, BindingResult result,
										ModelMap model, @PathVariable String description) {
		if (result.hasErrors()) {
			return "changeStatusTask";
		}

		taskService.updateTask(task);

		model.addAttribute("success", "Task " + task.getDescription() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "successaddtask";
	}


	
	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{username}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String username) {
		userService.deleteUserByUsername(username);
		return "redirect:/list";
	}

	@RequestMapping(value = { "/delete-project-{projectname}" }, method = RequestMethod.GET)
	public String deleteProject(@PathVariable String projectname) {
		projectService.deleteProjectByNameProject(projectname);
		return "redirect:/list";
	}

	@RequestMapping(value = { "/delete-task-{taskname}" }, method = RequestMethod.GET)
	public String deleteTask(@PathVariable String taskname) {
		taskService.deleteTaskByDescription(taskname);
		return "redirect:/list";
	}

	@RequestMapping(value = { "/delete-comment-{taskname}-{content}" }, method = RequestMethod.GET)
	public String deleteComment(@PathVariable String content, @PathVariable String taskname) {
		commentService.deleteCommentByContentComment(taskname,content);
		return "redirect:/list";
	}
	

	/**
	 * This method will provide UserRole list to views
	 */
	@ModelAttribute("roles")
	 public List<UserRole> initializeProfiles() {
		return userProfileService.findAll();
	}

	@ModelAttribute("statuses")
	public List<TaskStatus> initializeStatuses() {
		return taskStatusService.findAll();
	}

	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests.
	 * If users is already logged-in and tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
	    } else {
	    	return "redirect:/list";  
	    }
	}

	/**
	 * This method handles logout requests.
	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			//new SecurityContextLogoutHandler().logout(request, response, auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}


}