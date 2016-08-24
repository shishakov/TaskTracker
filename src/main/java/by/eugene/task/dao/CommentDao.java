package by.eugene.task.dao;

import java.util.List;

import by.eugene.task.model.Comment;


public interface CommentDao {

    Comment findById(int id);

    Comment findByContentComment(String contentComment);

    void save(Comment comment);

    void deleteByContentComment(String contentComment);

    List<Comment> findAllComments();

}

