package com.example.two.taskmanager.controllers;

import com.example.two.taskmanager.models.Task;
import com.example.two.taskmanager.models.User;
import com.example.two.taskmanager.repositories.TaskRepository;
import com.example.two.taskmanager.services.TaskServices;
import com.example.two.taskmanager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private TaskServices taskServices;

    @GetMapping("/task")
    public String getTaskPage(Model model){
        model.addAttribute("task",new Task());
        return "user/task";
    }

    @GetMapping("/tasklist")
    public String getTaskList(Model model){
        List<Task> tasks= taskServices.getTaskSortedByStatusForCurretnUser();
        model.addAttribute("tasks",tasks);
        return "user/tasklist";
    }

    @GetMapping("/add")
    public String showTaskForm(Model model){
        model.addAttribute("task",new Task());
        return "user/task";
    }

    @PostMapping("/add")
    public String  addTask(@ModelAttribute("task") Task task, Principal principal){
        User user = userServices.findByEmail(principal.getName());
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/task/tasklist";
    }
    @PostMapping("/tasks/updateStatus")
    public String updateTaskStatus(@RequestParam("taskId") Long taskId, @RequestParam("status") String status){
        Task task = taskServices.getTaskById(taskId);
        if (task != null){
            Task.Status newStatus = Task.Status.valueOf(status);
            task.setStatus(newStatus);
            taskServices.saveTask(task);
        }
        return "redirect:/task/tasklist";
    }


    @GetMapping("/editTask/{taskId}")
    public String showEditTaskForm(@PathVariable Long taskId, Model model){
        Task task = taskServices.getTaskById(taskId);
        if (task==null){
            throw new RuntimeException("Task not found");
        }
        model.addAttribute("task",task);
        return "user/editTask";
    }

    @PostMapping("/editTask/{taskId}")
    public String editTask(@ModelAttribute Task task){
        if(task.getId()==null){
            throw new IllegalArgumentException("The given id must not be null");
        }
        taskServices.updateTask(task);
        return "redirect:/task/tasklist";
    }

    @PostMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId){
        taskRepository.deleteById(taskId);
        return "redirect:/task/tasklist";
    }

    @GetMapping("/stats")
    @ResponseBody
    public Map<String, Long> getTaskStats(Principal principal) {
        User user = userServices.findByEmail(principal.getName());
        List<Task> userTasks = taskRepository.findByUser(user);

        return userTasks.stream()
                .collect(Collectors.groupingBy(
                        task -> task.getStatus().name(),
                        Collectors.counting()
                ));
    }

    @GetMapping("/latest")
    @ResponseBody
    public List<Task> getLatestTasks(Principal principal) {
        User user = userServices.findByEmail(principal.getName());
        return taskRepository.findTop5ByUserOrderByStartDateDesc(user);
    }


}



