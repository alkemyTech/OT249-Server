package com.alkemy.ong.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.service.ActivityService;



@RestController
@AllArgsConstructor
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/activities")
	public ResponseEntity<Activity> crearActividad(@Valid @RequestBody ActivityDto activityDto) {
		boolean deleted = false;
		Activity activity = new Activity(activityDto.getName(), activityDto.getContent(), activityDto.getImage(), new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>(activityService.crearActivity(activity), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/activities/{id}")
	public ResponseEntity<Activity> actualizarActividad(@Valid @RequestBody ActivityDto activityDto, @PathVariable("id") String id) {
		
		Activity activityEncontrada = activityService.findById(id);
		if (activityEncontrada == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			activityEncontrada.setName(activityDto.getName());
			activityEncontrada.setContent(activityDto.getContent());
			activityEncontrada.setImage(activityDto.getImage());
			activityEncontrada.setTimestamp(new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(activityService.crearActivity(activityEncontrada), HttpStatus.OK);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
