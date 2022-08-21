package com.alkemy.ong.service.impl;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	
	@Override
	public Activity crearActivity(Activity activity) {
		return activityRepository.save(activity);
	}

	@Override
	public Activity findById(String id) {
		Optional<Activity> activityOptional = activityRepository.findById(id);
		if (activityOptional.isPresent()) {
			return activityOptional.get();
		} else {
			return null;
		}
	}
	
}
