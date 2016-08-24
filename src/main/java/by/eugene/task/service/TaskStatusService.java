package by.eugene.task.service;

import java.util.List;

import by.eugene.task.model.TaskStatus;


public interface TaskStatusService {

    TaskStatus findById(int id);

    TaskStatus findByType(String type);

    List<TaskStatus> findAll();

}