package com.alkemy.ong.controller;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;

@RestController
public class MemberController {

	@Autowired
	private IMemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Operation(summary = "Create Member")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created - Miembro creado con éxito.",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Member.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Permission Denied ",
					content = @Content) })
	@PostMapping("members")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<String> createMember(@Valid @RequestBody MemberDto memberDto) {

		Member memberAux = new Member();

		memberAux.setName(memberDto.getName());

		memberAux.setFacebookUrl(memberDto.getFacebookUrl());

		memberAux.setInstagramUrl(memberDto.getInstagramUrl());

		memberAux.setLinkedinUrl(memberDto.getLinkedinUrl());

		memberAux.setImage(memberDto.getImage());

		memberAux.setDescription(memberDto.getDescription());

		memberAux.setTimestamp(new Timestamp(System.currentTimeMillis()));

		memberService.createMember(memberAux);

		return new ResponseEntity<>("Miembro creado con éxito", HttpStatus.CREATED);
	}

	@Operation(summary = "Delete Member")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok - Borrado con éxito.",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Member.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found - Miembro no encontrado",
					content = @Content)})
	@DeleteMapping("/members/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") String id) {

		if (memberRepository.existsById(id)) {

			memberService.deleteMemberById(id);

			return new ResponseEntity<String>("Borrado con éxito", HttpStatus.OK);
		} else {

			return new ResponseEntity<String>("Miembro no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "UpDate Member")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok - Miembro Actualizado Correctamente.",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Member.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found - Unable to find com.alkemy.ong.model.Member with id ...", //CONSULTAR A EMA
					content = @Content) })
	@PutMapping("/members/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<String> updateMember(@Valid @RequestBody MemberDto memberDto, @PathVariable String id){
		try{
			return new ResponseEntity<>(memberService.updateMember(memberDto, id), HttpStatus.OK);
		}catch(EntityNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get all Members")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK - The list of members.",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Member.class)) }),
			@ApiResponse(responseCode = "403", description = "Forbidden - Permission Denied ",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Members not found", //CONSULTAR A ELIAS
					content = @Content) })
	@Parameters(value = {
			@Parameter(name = "page", description = "Value = Page of the list - dataType = int")})
	@GetMapping("/members")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllMembers(@RequestParam(defaultValue = "0", name = "page") int page,
										   @RequestParam(defaultValue = "asc", name = "order") String order){
		return new ResponseEntity<>(memberService.getAllMembers(page, order),HttpStatus.OK);
	}
	
}
