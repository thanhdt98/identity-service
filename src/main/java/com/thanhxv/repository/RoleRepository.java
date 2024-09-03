package com.thanhxv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhxv.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
