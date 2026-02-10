package com.uriel.task_manager.service;

import com.uriel.task_manager.entity.TaskPriority;
import com.uriel.task_manager.entity.TaskStatus;
import com.uriel.task_manager.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.uriel.task_manager.dto.TaskRequest;
import com.uriel.task_manager.entity.Task;
import com.uriel.task_manager.repository.TaskRepository;
import com.uriel.task_manager.service.TaskService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setPriority(TaskPriority.HIGH);
        testTask.setStatus(TaskStatus.PENDING);
        testTask.setCreatedBy(1L);
        testTask.setCreatedDate(LocalDateTime.now());

        taskRequest = new TaskRequest();
        taskRequest.setTitle("New Task");
        taskRequest.setDescription("New Description");
        taskRequest.setPriority(TaskPriority.MEDIUM);
        taskRequest.setScheduledDateTime(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task createdTask = taskService.createTask(taskRequest, 1L);

        assertNotNull(createdTask);
        assertEquals(TaskStatus.PENDING, createdTask.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Collections.singletonList(testTask);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTasksByStatus() {
        List<Task> pendingTasks = Collections.singletonList(testTask);
        when(taskRepository.findByStatus(TaskStatus.PENDING)).thenReturn(pendingTasks);

        List<Task> result = taskService.getTasksByStatus(TaskStatus.PENDING);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TaskStatus.PENDING, result.getFirst().getStatus());
        verify(taskRepository, times(1)).findByStatus(TaskStatus.PENDING);
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(999L));
        verify(taskRepository, times(1)).findById(999L);
    }

    @Test
    void testApproveTaskAsManager() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task result = taskService.approveTask(1L, UserRole.MANAGER);
        assertNotNull(result);
        assertEquals(TaskStatus.APPROVED, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testApproveTaskAsAdmin() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task result = taskService.approveTask(1L, UserRole.ADMIN);
        assertNotNull(result);
        assertEquals(TaskStatus.APPROVED, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testApproveTaskAsUserShouldFail() {
        assertThrows(RuntimeException.class, () -> taskService.approveTask(1L, UserRole.USER));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testRejectTaskAsManager() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task result = taskService.rejectTask(1L, UserRole.MANAGER);

        assertNotNull(result);
        assertEquals(TaskStatus.REJECTED, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testRejectTaskAsUserShouldFail() {
        assertThrows(RuntimeException.class, () -> taskService.rejectTask(1L, UserRole.USER));
        verify(taskRepository, never()).save(any(Task.class));
    }
}
