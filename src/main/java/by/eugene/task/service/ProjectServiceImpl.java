package by.eugene.task.service;

import by.eugene.task.dao.UserDao;
import by.eugene.task.model.Project;
import by.eugene.task.dao.ProjectDao;

import java.util.*;

import by.eugene.task.model.Task;
import by.eugene.task.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao dao;

    @Autowired
    private UserDao userDao;

    public Project findById(int id) {
        return dao.findById(id);
    }

    public Project findByNameProject(String nameProject) {
        Project project = dao.findByNameProject(nameProject);
        return project;
    }

    public void saveProject(Project project) {
        dao.save(project);
    }

    public void updateProject(Project project) {
        Project entity = dao.findById(project.getId());
        if(entity!=null){
            entity.setNameProject(project.getNameProject());
            entity.setTasks(project.getTasks());
        }
    }

    public void saveProjectTask(Project project) {
        dao.saveMany(project);
    }

    public void deleteProjectByNameProject(String projects) {
        dao.deleteByNameProject(projects);
    }

    public List<Project> findAllProjects() {
        return dao.findAllProjects();
    }

    public List<Task> findAllTaskProject(String nameProject) {
        return dao.findByNameProjectTasks(nameProject);
    }

    public Set<Project> findAllProjectUser(String username) {
        List<Project> projectList = dao.findAllProjects();
        Set<Project> projectListDev =new HashSet<>();
        for(Project project: projectList){
            Set<Task> taskSet = new HashSet<>();
            taskSet = project.getTasks();
            for(Task task: taskSet){
                Set<User> userSet = new HashSet<>();
                userSet = task.getUsers();
                for(User user: userSet){
                    User userDev = userDao.findByUsername(username);
                    if(user.hashCode()==userDev.hashCode()){
                        projectListDev.add(project);
                    }
                }
            }
        }
        return projectListDev;
    }
}
