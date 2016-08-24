package by.eugene.task.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import by.eugene.task.model.TaskStatus;



@Repository("taskStatusDao")
public class TaskStatusDaoImpl extends AbstractDao<Integer, TaskStatus> implements TaskStatusDao{

    public TaskStatus findById(int id) {
        return getByKey(id);
    }

    public TaskStatus findByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (TaskStatus) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<TaskStatus> findAll(){
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<TaskStatus>)crit.list();
    }

}
