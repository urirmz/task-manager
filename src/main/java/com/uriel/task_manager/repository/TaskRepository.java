package com.uriel.task_manager.repository;

import com.uriel.task_manager.entity.Task;
import com.uriel.task_manager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedUserId(Long userId);

    List<Task> findAllByOrderByPriorityDesc();

    List<Task> findAllByOrderByCreatedDateTimeAsc();

}