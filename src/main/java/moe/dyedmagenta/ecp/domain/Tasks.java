package moe.dyedmagenta.ecp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Tasks.
 */
@Entity
@Table(name = "tasks")
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name_of_task")
    private String nameOfTask;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public Tasks nameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
        return this;
    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public Tasks startTime(LocalDate startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public Tasks endTime(LocalDate endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public Tasks user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tasks tasks = (Tasks) o;
        if (tasks.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tasks.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tasks{" +
            "id=" + getId() +
            ", nameOfTask='" + getNameOfTask() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
