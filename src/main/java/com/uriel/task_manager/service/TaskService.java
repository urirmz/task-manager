package com.uriel.task_manager.service;

import com.uriel.task_manager.dto.TaskRequest;
import com.uriel.task_manager.entity.Task;
import com.uriel.task_manager.entity.TaskStatus;
import com.uriel.task_manager.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setAssignedUserId(request.getAssignedUserId());
        task.setScheduledDateTime(request.getScheduledDateTime());
        task.setStatus(TaskStatus.PENDING);

        Task savedTask = taskRepository.save(task);
        System.out.println("✓ Task created: " + savedTask.getTitle() + " (ID: " + savedTask.getId() + ")");
        return savedTask;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    @Transactional
    public Task approveTask(Long taskId) {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.APPROVED);
        Task updatedTask = taskRepository.save(task);

        System.out.println("✓ NOTIFICATION: Task '" + updatedTask.getTitle() + "' has been APPROVED");
        return updatedTask;
    }

    @Transactional
    public Task rejectTask(Long taskId) {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.REJECTED);
        Task updatedTask = taskRepository.save(task);

        System.out.println("✗ NOTIFICATION: Task '" + updatedTask.getTitle() + "' has been REJECTED");
        return updatedTask;
    }

}