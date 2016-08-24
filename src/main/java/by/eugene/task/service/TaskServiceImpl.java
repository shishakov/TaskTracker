package by.eugene.task.service;

import by.eugene.task.dao.ProjectDao;
import by.eugene.task.model.Comment;
import by.eugene.task.model.Task;
import by.eugene.task.dao.TaskDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("taskService")
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao dao;

    @Autowired
    private ProjectDao projectDao;

    public Task findById(int id) {
        return dao.findById(id);
    }

    public Task findByDescription(String description) {
        Task task = dao.findByDescription(description);
        return task;
    }

    public void saveTask(Task task) {
        dao.save(task);
    }

    @Override
    public void saveTaskComment(Task task) {
        dao.saveMany(task);
    }

    public void updateTask(Task task) {
        Task entity = dao.findById(task.getId());
        if(entity!=null){
            entity.setDescription(task.getDescription());
            entity.setTaskSet(task.getTaskSet());
            entity.setTaskStatuses(task.getTaskStatuses());
        }
    }

    public void deleteTaskByDescription(String description) {
        dao.deleteByDescription(description);
    }

    public List<Task> findAllTasks() {
        return dao.findAllTasks();
    }

    public List<Task> findAllTasksProject(String projectName) {
        return projectDao.findByNameProjectTasks(projectName);
    }

    public List<Comment> findAllTaskComments(String taskname) {
        Task task = dao.findByDescription(taskname);
        List<Comment> comments = new ArrayList<>(task.getTaskSet());
        return comments;
    }
}
