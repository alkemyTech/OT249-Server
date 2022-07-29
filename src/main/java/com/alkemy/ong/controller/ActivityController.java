package com.alkemy.ong.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.service.ActivityService;

@RestController
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	@PostMapping("/activities")
	public ResponseEntity<Activity> crearActividad(@Valid @RequestBody ActivityDto activityDto) {
		boolean deleted = false;
		Activity activity = new Activity(activityDto.getName(), activityDto.getContent(), activityDto.getImage(), new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>(activityService.crearActivity(activity), HttpStatus.OK);
	}
	
}
