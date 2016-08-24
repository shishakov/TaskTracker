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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="PROJECT")
public class Project implements Serializable{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name="NAME_PROJECT", nullable=false)
    private String nameProject;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "TASK_PROJECT",
            joinColumns = { @JoinColumn(name = "PROJECT_ID", insertable = false, updatable = false, nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "TASK_ID", insertable = false, updatable = false, nullable = false) })
    private Set<Task> tasks = new HashSet<Task>();

    public Project() {
    }

    public Project(String nameProject) {
        this.nameProject = nameProject;
    }

    public Project(String nameProject, Set<Task> tasks) {
        this.nameProject = nameProject;
        this.tasks = tasks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        return !(nameProject != null ? !nameProject.equals(project.nameProject) : project.nameProject != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nameProject != null ? nameProject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", nameProject='" + nameProject + '\'' +
                '}';
    }
}
