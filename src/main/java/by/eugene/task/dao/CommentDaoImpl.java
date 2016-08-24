package by.eugene.task.dao;

import by.eugene.task.model.Comment;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository("commentDao")
public class CommentDaoImpl extends AbstractDao<Integer, Comment> implements CommentDao {

    static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);

    @Override
    public Comment findById(int id) {
        Comment comment = getByKey(id);
        return comment;
    }

    @Override
    public Comment findByContentComment(String contentComment) {
        logger.info("Comment : {}", contentComment);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("contentComment", contentComment));
        Comment comment = (Comment)crit.uniqueResult();
        return comment;
    }

    @Override
    public void save(Comment comment) {
        persist(comment);
    }

    public void deleteByContentComment(String contentComment) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("contentComment", contentComment));
        Comment comment = (Comment)crit.uniqueResult();
        delete(comment);
    }

    @Override
    public List<Comment> findAllComments() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("contentComment"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Comment> comments = (List<Comment>) criteria.list();
        return comments;
    }
}
