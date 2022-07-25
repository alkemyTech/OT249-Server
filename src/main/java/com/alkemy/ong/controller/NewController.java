package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class NewController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news/{id}")
    public ResponseEntity<NewDTO> NewDetail(@PathVariable UUID id){

        NewDTO newDTO = newsService.getNews(id); //TODO Ver Servicio de News (Id como String)

    }
    @PutMapping("/news/{id}")
    public ResponseEntity<NewDTO> updateNews(@PathVariable String id, @Valid @RequestBody NewDTO newsDTO, BindingResult bindingResult){

        NewDTO newsDTOresponse = newsService.updateNews(id, newsDTO, bindingResult );
        return ResponseEntity.ok( newsDTOresponse );
    }
}
