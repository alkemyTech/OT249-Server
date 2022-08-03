package com.alkemy.ong.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

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

	@PostMapping("members")
	@PreAuthorize("hasRole('USER')")
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
	
	
	@GetMapping("/members")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllMembers(@RequestParam(defaultValue = "0", name = "page") int page,
										   @RequestParam(defaultValue = "asc", name = "order") String order){
		return new ResponseEntity<>(memberService.getAllMembers(page, order),HttpStatus.OK);
	}
	
}
