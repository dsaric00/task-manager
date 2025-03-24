package com.example.two.taskmanager.services;

import com.example.two.taskmanager.models.Task;
import com.example.two.taskmanager.models.User;
import com.example.two.taskmanager.repositories.TaskRepository;
import com.example.two.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServices {

    private final TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public TaskServices(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getTaskSortedByStatus(){
        return taskRepository.findAll(Sort.by("status").ascending());
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id: " +id));
    }
    public List<Task> getTasksforCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(authentication.getName());
        return taskRepository.findByUser(currentUser);
    }

    public List<Task> getTaskSortedByStatusForCurretnUser(){
        List<Task> tasks = getTasksforCurrentUser();
        tasks.sort((t1,t2) -> t1.getStatus().compareTo(t2.getStatus()));
        return tasks;
    }

    public void updateTask(Task updateTask){
        Task existingTask = taskRepository.findById(updateTask.getId())
                .orElseThrow(()-> new IllegalArgumentException("Invalid task Id: "+ updateTask.getId()));
        existingTask.setTitle(updateTask.getTitle());
        existingTask.setDescription(updateTask.getDescription());
        existingTask.setStartDate(updateTask.getStartDate());
        existingTask.setEndDate(updateTask.getEndDate());
        existingTask.setStatus(updateTask.getStatus());
        taskRepository.save(existingTask);
    }
}
