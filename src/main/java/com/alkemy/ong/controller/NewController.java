package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CreateNewsDto;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class NewController {

	private final NewsService newsService;
	
	private final CategoryRepository categoryRepository;

	@Operation(summary = "Get all News")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = NewDTO.class)) }),
			@ApiResponse(responseCode = "403", description = "Forbidden - Permission Denied ",
					content = @Content)})
	@Parameters(value = {
			@Parameter(name = "page", description = "Value = 0 - dataType = int"),
			@Parameter(name = "order", description = "Value = asc - dataType = String")})
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PageDto<NewDTO>> getPagedController(
			@RequestParam(defaultValue = "0", name = "page") int page,
			@RequestParam(defaultValue = "asc", name = "order") String order) {
		PageDto<NewDTO> newDTO = newsService.getAllNews(page, order);
		return ResponseEntity.ok(newDTO);
	}

	@Operation(summary = "Get New details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = NewDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found",
					content = @Content) })
	@Parameters(value = {
			@Parameter(name = "id", description = "New id we want get", required = true)})
	@GetMapping("/{id}")
	public ResponseEntity<NewDTO> NewDetail(@PathVariable String id) {
		try {
			NewDTO newDTO = newsService.getNews(id);
			return ResponseEntity.ok().body(newDTO);
		}catch (Exception e){
			return ResponseEntity.notFound().build();
		}

	}

	@Operation(summary = "Delete New")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Permission Denied ",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found - New not found",
					content = @Content) })
	@Parameters(value = {
			@Parameter(name = "id", description = "New id we want delete", required = true)})
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteNews(@PathVariable String id) {
		News news = newsService.findNewsById(id);
		if (news != null) {
			newsService.deleteNews(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@Operation(summary = "UpDate New")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = NewDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request - Request processing failed",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Permission Denied ",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found - New not found",
					content = @Content) })
	@Parameters(value = {
			@Parameter(name = "id", description = "New id we want to update", required = true)})
	@PutMapping("/{id}")
	@PreAuthorize( "hasRole('ADMIN')" )
	public ResponseEntity<NewDTO> updateNews(@PathVariable String id, @Valid @RequestBody NewDTO newsDTO, BindingResult bindingResult){

		NewDTO newsDTOresponse = newsService.updateNews(id, newsDTO, bindingResult );
		return ResponseEntity.ok( newsDTOresponse );
	}
	
	@Operation(summary = "Create New")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created - Novedad creada correctamente.",
					content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Permission Denied ",
					content = @Content) })
	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createNews(@Valid @RequestBody CreateNewsDto createNewsDto){

    	News newsAux = new News();
    	Category categoryAux = categoryRepository.findById(createNewsDto.getIdCategory()).get();
  
    	newsAux.setName(createNewsDto.getName());
    	newsAux.setContent(createNewsDto.getContent());
    	newsAux.setImage(createNewsDto.getImage());
    	newsAux.setCategory(categoryAux);
    	newsAux.setTimestamp(LocalDateTime.now());
    	newsAux.setSoftDelete(false);
    	
    	newsService.createNews(newsAux);
    	
    	return new ResponseEntity<>("Novedad creada correctamente",HttpStatus.CREATED);
    	
    	
    	

    }
}
