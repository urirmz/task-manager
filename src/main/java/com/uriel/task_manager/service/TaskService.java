package com.uriel.task_manager.service;

import com.uriel.task_manager.dto.TaskRequest;
import com.uriel.task_manager.entity.Task;
import com.uriel.task_manager.entity.TaskStatus;
import com.uriel.task_manager.entity.User;
import com.uriel.task_manager.repository.TaskRepository;
import com.uriel.task_manager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Task createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());

        if (request.getAssignedUserId() != null) {
            User user = userRepository.findById(request.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAssignedUserId()));
            task.setAssignedUser(user);
        }

        task.setScheduledDateTime(request.getScheduledDateTime());
        task.setStatus(TaskStatus.PENDING);

        Task savedTask = taskRepository.save(task);
        logger.info("✓ Task created: {} (ID: {})", savedTask.getTitle(), savedTask.getId());
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

        logger.info("✓ NOTIFICATION: Task '{}' has been APPROVED", updatedTask.getTitle());
        return updatedTask;
    }

    @Transactional
    public Task rejectTask(Long taskId) {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.REJECTED);
        Task updatedTask = taskRepository.save(task);

        logger.info("✗ NOTIFICATION: Task '{}' has been REJECTED", updatedTask.getTitle());
        return updatedTask;
    }

}