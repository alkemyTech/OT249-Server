package com.alkemy.ong.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Slide;


@Service
public interface SlideServices {

    public List<Slide> getAll();

    public Slide get(Long id);

    public void delete(Long id);

    public Slide update(Long id, Slide slide);
    
}