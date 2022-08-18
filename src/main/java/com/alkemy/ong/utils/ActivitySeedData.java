package com.alkemy.ong.utils;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;

@Component
@Profile("!test")
public class ActivitySeedData implements CommandLineRunner {

	@Autowired
	private ActivityRepository activityRepository;
	
	@Override
	public void run(String... args) throws Exception {
		this.saveActivity();
	}

	
	private void saveActivity() {
		if (activityRepository.count() == 0) {
			boolean deleted = false;
			Activity activity1 = new Activity("Educacion fisica", "Aqui se encuentra lo relacionado con la ed fisica", "Sin imagen", new Timestamp(System.currentTimeMillis()), deleted);
			Activity activity2 = new Activity("Lectura", "Se podra leer todo tipo de libros", "Sin imagen", new Timestamp(System.currentTimeMillis()), deleted);
			Activity activity3 = new Activity("Futbol", "Podras unirte a un equipo de futbol", "Sin imagen", new Timestamp(System.currentTimeMillis()), deleted);
			Activity activity4 = new Activity("Programacion", "Aprenderas desde lo mas basico hasta nivel avanzado en logica de programacion","Sin imagen", new Timestamp(System.currentTimeMillis()), deleted);
			
			activityRepository.save(activity1);
			activityRepository.save(activity2);
			activityRepository.save(activity3);
			activityRepository.save(activity4);
		}
	}
	
	
}
