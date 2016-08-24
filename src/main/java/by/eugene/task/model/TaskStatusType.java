package by.eugene.task.model;

import java.io.Serializable;

public enum TaskStatusType implements Serializable{
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED");

    String taskStatusType;

    private TaskStatusType(String taskStatusType){
        this.taskStatusType = taskStatusType;
    }

    public String getTaskStatusType() {
        return taskStatusType;
    }
}
