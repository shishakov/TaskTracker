package by.eugene.task.service;

import by.eugene.task.dao.TaskDao;
import by.eugene.task.model.Comment;
import by.eugene.task.dao.CommentDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import by.eugene.task.model.Project;
import by.eugene.task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao dao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public Comment findById(int id) {
        return dao.findById(id);
    }

    @Override
    public Comment findByContentComment(String contentComment) {
        Comment comment = dao.findByContentComment(contentComment);
        return comment;
    }

    @Override
    public void saveComment(Comment comment) {
        dao.save(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        Comment entity = dao.findById(comment.getId());
        if(entity!=null){
            entity.setContentComment(comment.getContentComment());
        }
    }

    @Override
    public void deleteCommentByContentComment(String description, String contentComment) {
        Task task = taskDao.findByDescription(description);
        Comment comment = dao.findByContentComment(contentComment);
        Set<Comment> comments = task.getTaskSet();
        comments.remove(comment);
        task.setTaskSet(comments);
        taskDao.save(task);

        dao.deleteByContentComment(contentComment);
    }

    @Override
    public List<Comment> findAllComments() {
        return dao.findAllComments();
    }
}
