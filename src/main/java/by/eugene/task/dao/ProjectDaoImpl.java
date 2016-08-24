package by.eugene.task.dao;

import java.util.ArrayList;
import java.util.List;

import by.eugene.task.model.Task;
import by.eugene.task.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import by.eugene.task.model.Project;

@Repository("projectDao")
public class ProjectDaoImpl extends AbstractDao<Integer, Project> implements ProjectDao {

    static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);

    public Project findById(int id) {
        Project project = getByKey(id);
        return project;
    }

    public Project findByNameProject(String nameProject) {
        logger.info("Project : {}", nameProject);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("nameProject", nameProject));
        Project project = (Project)crit.uniqueResult();
        Hibernate.initialize(project.getTasks());
        return project;
    }

    @Override
    public List<Task> findByNameProjectTasks(String nameProject) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("nameProject", nameProject));
        Project project = (Project)crit.uniqueResult();
        Hibernate.initialize(project.getTasks());
        List<Task> projectTasks = new ArrayList<>();
        for(Task task: project.getTasks()) {
            projectTasks.add(task);
        }
        return projectTasks;
    }

    public void save(Project project) {
        persist(project);
    }

    public void saveMany(Project project) {
        saveTrue(project);
    }

    public void deleteByNameProject(String nameProject) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("nameProject", nameProject));
        Project project = (Project)crit.uniqueResult();
        delete(project);
    }

    public List<Project> findAllProjects() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("nameProject"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Project> projects = (List<Project>) criteria.list();
        return projects;
    }
}
