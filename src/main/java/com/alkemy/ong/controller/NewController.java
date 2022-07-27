package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.model.News;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewController {

	@Autowired
	private NewsService newsService;

	@GetMapping("/{id}")
	public ResponseEntity<NewDTO> NewDetail(@PathVariable UUID id) {

		NewDTO newDTO = newsService.getNews(id); // TODO Ver Servicio de News (Id como String)

		return ResponseEntity.ok().body(newDTO);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/news/{id}")
	public ResponseEntity<Boolean> deleteNews(@PathVariable UUID id) {
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
	public ResponseEntity<NewDTO> updateNews(@PathVariable UUID id, @Valid @RequestBody NewDTO newsDTO, BindingResult bindingResult){

		NewDTO newsDTOresponse = newsService.updateNews(id, newsDTO, bindingResult );
		return ResponseEntity.ok( newsDTOresponse );
	}
}
