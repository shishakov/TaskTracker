package by.eugene.task.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="COMMENT")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "CONTENT_COMMENT", nullable = false)
    private String contentComment;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "taskSet")
    private Set<Task> taskComments = new HashSet<Task>();

    public Comment() {
    }

    public Comment(String contentComment) {
        this.contentComment = contentComment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public Set<Task> getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(Set<Task> taskComments) {
        this.taskComments = taskComments;
    }
}
