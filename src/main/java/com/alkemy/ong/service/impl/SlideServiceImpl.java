package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Slide;
import com.alkemy.ong.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {

    @Override
    public List<Slide> getAll() {
        return null;
    }

    @Override
    public Slide get(UUID id) {
        return null;
    }

    @Override
    public void delete(UUID id) {
    }

    @Override
    public Slide update(UUID id, Slide slide) {
        return null;
    }
    
}
