package com.authentication.login.controller;

import com.authentication.login.entity.TaskEntity;
import com.authentication.login.entity.UserEntity;
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

    @PutMapping("/edit/{id}")
    public GenericResponse<TaskEntity> updateTask(@PathVariable String id, @RequestBody TaskEntity updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/delete/{id}")
    public GenericResponse<TaskEntity> deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }
}
