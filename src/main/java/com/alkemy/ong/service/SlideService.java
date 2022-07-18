package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.model.Slide;

public interface SlideService {
    
    List<Slide> getAll();
    Slide get(Long id);
    void delete(Long id);
    Slide update(Long id, Slide slide);
}
