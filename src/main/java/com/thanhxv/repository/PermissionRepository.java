package com.thanhxv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhxv.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
