package com.authentication.login.service;

import com.authentication.login.entity.TaskEntity;
import com.authentication.login.exceptions.AuthExceptions;
import com.authentication.login.repository.TaskRepo;
import com.authentication.login.util.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TaskService {

    private final TaskRepo taskRepo;

    public GenericResponse<List<TaskEntity>> getTask() {
        List<TaskEntity> task = taskRepo.findAll();
        if (!task.isEmpty()) {
            return new GenericResponse<>(HttpStatus.OK, "Task found", task);
        } else {
            throw new AuthExceptions.NotFoundException("Task Not Found");
        }
    }

    public GenericResponse<TaskEntity> createTask(TaskEntity task) {
        TaskEntity tmp = new TaskEntity();
        tmp.setTaskId(UUID.randomUUID().toString());
        tmp.setTitle(task.getTitle());
        tmp.setStatus(0);
        TaskEntity savedTask = taskRepo.save(tmp);

        return new GenericResponse<>(HttpStatus.CREATED, "Task created successfully", savedTask);
    }

    public GenericResponse<TaskEntity> updateTask(String id, TaskEntity updatedTask) {
        TaskEntity existingTask = taskRepo.findById(String.valueOf(id))
                .orElseThrow(() -> new AuthExceptions.NotFoundException("Task not found with id: " + id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setStatus(updatedTask.getStatus());

        TaskEntity saved = taskRepo.save(existingTask);
        return new GenericResponse<>(HttpStatus.OK, "Task updated successfully", saved);
    }

    public GenericResponse<TaskEntity> deleteTask(String id) {
        TaskEntity existingTask = taskRepo.findById(id)
                .orElseThrow(() -> new AuthExceptions.NotFoundException("Task not found with id: " + id));

        taskRepo.delete(existingTask);
        return new GenericResponse<>(HttpStatus.OK, "Task deleted successfully", existingTask);
    }

}
