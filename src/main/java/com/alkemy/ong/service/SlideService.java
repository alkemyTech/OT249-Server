package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import com.alkemy.ong.model.Slide;

public interface SlideService {
    
    List<Slide> getAll();
    Slide get(String id);
    void delete(String id);
    Slide update(String id, Slide slide);
}
