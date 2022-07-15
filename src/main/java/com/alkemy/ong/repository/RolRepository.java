package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
