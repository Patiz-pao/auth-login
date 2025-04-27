package com.authentication.login.repository;

import com.authentication.login.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleEntity, String> {

    RoleEntity findByRoleId(String roleId);
}
