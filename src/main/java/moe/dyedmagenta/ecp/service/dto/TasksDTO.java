package moe.dyedmagenta.ecp.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Tasks entity.
 */
public class TasksDTO implements Serializable {

    private Long id;

    private String nameOfTask;

    private LocalDate startTime;

    private LocalDate endTime;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TasksDTO tasksDTO = (TasksDTO) o;
        if(tasksDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tasksDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TasksDTO{" +
            "id=" + getId() +
            ", nameOfTask='" + getNameOfTask() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
