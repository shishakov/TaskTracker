package by.eugene.task.dao;

import java.util.List;

import by.eugene.task.model.TaskStatus;


public interface TaskStatusDao {

    List<TaskStatus> findAll();

    TaskStatus findByType(String type);

    TaskStatus findById(int id);
}
