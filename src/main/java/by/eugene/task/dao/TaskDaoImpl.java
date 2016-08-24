package by.eugene.task.dao;

import by.eugene.task.model.Task;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("taskDao")
public class TaskDaoImpl extends AbstractDao<Integer, Task> implements TaskDao {

    static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);

    public Task findById(int id) {
        Task task = getByKey(id);
        return task;
    }

    public Task findByDescription(String description) {
        logger.info("Task : {}", description);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("description", description));
        Task task = (Task)crit.uniqueResult();
        if(task!=null){
            Hibernate.initialize(task.getTaskSet());
            Hibernate.initialize(task.getTaskStatuses());
        }
        return task;
    }

    public void save(Task task) {
        persist(task);
    }

    public void saveMany(Task task) {
        saveTrue(task);
    }

    public void deleteByDescription(String description) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("description", description));
        Task task = (Task)crit.uniqueResult();
        delete(task);
    }

    public List<Task> findAllTasks() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("description"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Task> tasks = (List<Task>) criteria.list();
        for(Task task : tasks){
            Hibernate.initialize(task.getTaskSet());
            Hibernate.initialize(task.getTaskStatuses());
        }
        return tasks;
    }

}
