package by.eugene.task.service;

import java.util.List;
import java.util.Set;

import by.eugene.task.model.Comment;
import by.eugene.task.model.Task;

public interface TaskService {

    Task findById(int id);

    Task findByDescription(String description);

    void saveTask(Task task);

    void saveTaskComment(Task task);

    void updateTask(Task task);

    void deleteTaskByDescription(String description);

    List<Task> findAllTasks();

    List<Task> findAllTasksProject(String projectName);

    List<Comment> findAllTaskComments(String taskname);
}