package com.alkemy.ong.repository;

import com.alkemy.ong.model.Slide;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide, String> {
    Slide findTopByOrderByPositionDesc();

    List<Slide> findByOrganization_idLikeOrderByPositionDesc(String ongId);
}