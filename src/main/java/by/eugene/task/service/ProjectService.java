package by.eugene.task.service;

import java.util.List;
import java.util.Set;

import by.eugene.task.model.Project;
import by.eugene.task.model.Task;

public interface ProjectService {

    Project findById(int id);

    Project findByNameProject(String nameProject);

    void saveProject(Project project);

    void updateProject(Project project);

    void saveProjectTask(Project project);

    void deleteProjectByNameProject(String projects);

    List<Project> findAllProjects();

    List<Task> findAllTaskProject(String nameProject);

    Set<Project> findAllProjectUser(String username);
}
