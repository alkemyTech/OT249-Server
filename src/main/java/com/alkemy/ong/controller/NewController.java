package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.model.News;
import com.alkemy.ong.dto.CreateNewsDto;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
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

    
    @PostMapping("/news")
    public ResponseEntity<String> createNews(@RequestBody CreateNewsDto createNewsDto){

    	News newsAux = new News();
    	
    	newsAux.setName(createNewsDto.getName());
    	newsAux.setContent(createNewsDto.getName());
    	newsAux.setImage(createNewsDto.getImage());
    	newsAux.setCategory(createNewsDto.getCategory());
    	newsAux.setTimestamp(LocalDateTime.now());
    	newsAux.setSoftDelete(false);
    	
    	newsService.createNews(newsAux);
    	
    	return new ResponseEntity<>("Novedad creada correctamente",HttpStatus.CREATED);
    	
    	
    	

    }
}
