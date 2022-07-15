package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
