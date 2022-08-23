package com.alkemy.ong.controller;


import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.service.TestimonialService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping(value = "/testimonials")
@AllArgsConstructor
public class TestimonialController {
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Endpoint to create Testimony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Testimonial created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestimonialDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Testimonial not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
            content = @Content) })
    public ResponseEntity<TestimonialDto> createTestimony(@Valid @RequestBody TestimonialDto testimonialDto, BindingResult bindingResult) {

        TestimonialDto newTestimonialDto = testimonialService.createTestimony( testimonialDto, bindingResult);
        return new ResponseEntity<>( newTestimonialDto, HttpStatus.CREATED);
    }

    private final TestimonialService testimonialService;

    @Operation(summary = "Endpoint to update Testimony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Testimonial uddated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestimonialDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Testimonial not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
                    content = @Content) })
    @PutMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<TestimonialDto> updateTestimonial(@PathVariable String id, @Valid @RequestBody TestimonialDto testimonialDto, BindingResult bindingResult){

        TestimonialDto updateTestimony = testimonialService.updateTestimony(id, testimonialDto, bindingResult );
        return ResponseEntity.ok( updateTestimony );
    }

    @Operation(summary = "Endpoint to delete Testimony")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Testimonial deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestimonialDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Testimonial not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
                    content = @Content) })
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<?> deleteTestimonial(@PathVariable String id){
        testimonialService.deleteTestimony(id );
        return ResponseEntity.noContent().build();
    }
    
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<PageDto<TestimonialDto>> getPagedController(
			@RequestParam(defaultValue = "0", name = "page") int page,
			@RequestParam(defaultValue = "asc", name = "order") String order) {
		PageDto<TestimonialDto> testimonialDTO = testimonialService.getAllTestimonials(page, order);
		return ResponseEntity.ok(testimonialDTO);
	}

}
