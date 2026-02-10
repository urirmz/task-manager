package com.uriel.task_manager.controller;

import com.uriel.task_manager.dto.TaskRequest;
import com.uriel.task_manager.entity.Task;
import com.uriel.task_manager.entity.TaskStatus;
import com.uriel.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(@RequestParam(required = false) String status) {
        List<Task> tasks;
        if (status != null && !status.isEmpty()) {
            tasks = taskService.getTasksByStatus(TaskStatus.valueOf(status.toUpperCase()));
        } else {
            tasks = taskService.getAllTasks();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest) {
        Task task = taskService.createTask(taskRequest);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveTask(@PathVariable Long id) {
        Task task = taskService.approveTask(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectTask(@PathVariable Long id) {
        Task task = taskService.rejectTask(id);
        return ResponseEntity.ok(task);
    }

}