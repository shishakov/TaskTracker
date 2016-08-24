package by.eugene.task.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="TASK")
public class Task implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name="DESCRIPTION", nullable=false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TASK_STATUS",
            joinColumns = { @JoinColumn(name = "TASK_ID") },
            inverseJoinColumns = { @JoinColumn(name = "STATUS_ID") })
    private Set<TaskStatus> taskStatuses = new HashSet<TaskStatus>(0);

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name = "TASK_COMMENT",
            joinColumns = {@JoinColumn(name = "TASK_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "COMMENT_ID", nullable = false, updatable = false)})
    private Set<Comment> taskSet = new HashSet<Comment>(0);

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "tasks")
    private Set<Project> projects = new HashSet<Project>(0);

    @ManyToMany(cascade=CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "taskus")
    private Set<User> users = new HashSet<User>(0);

    public Task() {

    }

    public Task(String description) {
        this.description = description;
    }

    public Task(String description, Set<Project> projects) {
        this.description = description;
        this.projects = projects;
    }

    public Task(String description, Set<Project> projects, Set<User> users) {
        this.description = description;
        this.projects = projects;
        this.users = users;
    }

    public Task(String description, Set<Project> projects, Set<Comment> comments, Set<User> users) {
        this.description = description;
        this.projects = projects;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Comment> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Comment> taskSet) {
        this.taskSet = taskSet;
    }

    public Set<TaskStatus> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(Set<TaskStatus> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        return !(description != null ? !description.equals(task.description) : task.description != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
