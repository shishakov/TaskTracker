package by.eugene.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.eugene.task.dao.TaskStatusDao;
import by.eugene.task.model.TaskStatus;


@Service("taskStatusService")
@Transactional
public class TaskStatusServiceImpl implements TaskStatusService{

    @Autowired
    TaskStatusDao dao;

    public TaskStatus findById(int id) {
        return dao.findById(id);
    }

    public TaskStatus findByType(String type){
        return dao.findByType(type);
    }

    public List<TaskStatus> findAll() {
        return dao.findAll();
    }
}
