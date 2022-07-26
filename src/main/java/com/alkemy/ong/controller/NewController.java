package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/{id}")
    public ResponseEntity<NewDTO> NewDetail(@PathVariable UUID id){

        NewDTO newDTO = newsService.getNews(id); //TODO Ver Servicio de News (Id como String)

        return ResponseEntity.ok().body(newDTO);
    }

}
