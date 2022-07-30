package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CreateNewsDto;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.model.News;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import javax.validation.Valid;

@RestController
@RequestMapping("/news")
public class NewController {

	@Autowired
	private NewsService newsService;

	@GetMapping("/{id}")
	public ResponseEntity<NewDTO> NewDetail(@PathVariable String id) {

		NewDTO newDTO = newsService.getNews(id); // TODO Ver Servicio de News (Id como String)

		return ResponseEntity.ok().body(newDTO);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/news/{id}")
	public ResponseEntity<Boolean> deleteNews(@PathVariable String id) {
		News news = newsService.findNewsById(id);
		if (news != null) {
			newsService.deleteNews(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/{id}")
	@PreAuthorize( "hasAuthority('ADMIN')" )
	public ResponseEntity<NewDTO> updateNews(@PathVariable String id, @Valid @RequestBody NewDTO newsDTO, BindingResult bindingResult){

		NewDTO newsDTOresponse = newsService.updateNews(id, newsDTO, bindingResult );
		return ResponseEntity.ok( newsDTOresponse );
	}
	
    @PostMapping("/news")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
