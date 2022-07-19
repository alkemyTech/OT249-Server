package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import com.alkemy.ong.model.Slide;

public interface SlideService {
    
    List<Slide> getAll();
    Slide get(UUID id);
    void delete(UUID id);
    Slide update(UUID id, Slide slide);
}
