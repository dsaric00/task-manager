package com.example.two.taskmanager.repositories;

import com.example.two.taskmanager.models.Task;
import com.example.two.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByStatus(@Param("status") String status);

    List<Task> searchTaskByTitle(@Param("title") String title);

    List<Task> findByUser(User user);

}
