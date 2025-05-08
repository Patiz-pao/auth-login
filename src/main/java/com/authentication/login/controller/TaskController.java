package com.authentication.login.controller;

import com.authentication.login.entity.TaskEntity;
import com.authentication.login.service.TaskService;
import com.authentication.login.util.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/view")
    public GenericResponse<List<TaskEntity>> getTask() {
        GenericResponse<List<TaskEntity>> response = taskService.getTask();
        log.info("List of all tasks: {}", response.getData().size());
        return response;
    }

    @PostMapping("/create")
    public GenericResponse<TaskEntity> createTask(@RequestBody TaskEntity task) {
        return taskService.createTask(task);
    }

    @PutMapping("/edit")
    public GenericResponse<TaskEntity> updateTask(@RequestParam String taskId, @RequestBody TaskEntity updatedTask) {
        return taskService.updateTask(taskId, updatedTask);
    }

    @DeleteMapping("/delete")
    public GenericResponse<TaskEntity> deleteTask(@RequestParam String taskId) {
        return taskService.deleteTask(taskId);
    }
}
