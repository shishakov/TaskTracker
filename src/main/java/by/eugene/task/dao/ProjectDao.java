package by.eugene.task.dao;

import java.util.List;

import by.eugene.task.model.Project;
import by.eugene.task.model.Task;


public interface ProjectDao {

    Project findById(int id);

    Project findByNameProject(String nameProject);

    List<Task> findByNameProjectTasks(String nameProject);

    void save(Project project);

    void saveMany(Project project);

    void deleteByNameProject(String projects);

    List<Project> findAllProjects();

}

