package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Slide;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long>{
    
}
