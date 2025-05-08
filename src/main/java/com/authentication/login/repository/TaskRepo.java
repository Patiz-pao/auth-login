package com.authentication.login.repository;

import com.authentication.login.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<TaskEntity, String> {

    TaskEntity findByTaskId(String taskId);
}
