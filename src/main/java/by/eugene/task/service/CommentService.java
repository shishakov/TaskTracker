package by.eugene.task.service;

import java.util.List;

import by.eugene.task.model.Comment;

public interface CommentService {

    Comment findById(int id);

    Comment findByContentComment(String contentComment);

    void saveComment(Comment comment);

    void updateComment(Comment comment);

    void deleteCommentByContentComment(String description, String contentComment);

    List<Comment> findAllComments();
}
