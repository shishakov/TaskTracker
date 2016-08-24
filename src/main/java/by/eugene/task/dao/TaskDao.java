package by.eugene.task.dao;

import java.util.List;

import by.eugene.task.model.Comment;
import by.eugene.task.model.Task;


public interface TaskDao {

    Task findById(int id);

    Task findByDescription(String description);

    void save(Task task);

    void saveMany(Task task);

    void deleteByDescription(String description);

    List<Task> findAllTasks();

}