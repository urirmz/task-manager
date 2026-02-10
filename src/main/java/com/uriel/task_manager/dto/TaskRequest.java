package com.uriel.task_manager.dto;

import com.uriel.task_manager.entity.TaskPriority;

import java.time.LocalDateTime;

public class TaskRequest {

    private String title;
    private String description;
    private LocalDateTime scheduledDateTime;
    private TaskPriority priority;
    private Long assignedUserId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    @Override
    public String toString() {
        return "TaskRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", scheduledDateTime=" + scheduledDateTime +
                ", priority=" + priority +
                ", assignedUserId=" + assignedUserId +
                '}';
    }

}