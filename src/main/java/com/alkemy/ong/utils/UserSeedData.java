package com.alkemy.ong.utils;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;

@Component
@Profile("!test")
public class UserSeedData implements CommandLineRunner {
    
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
	
    private static final String PASSWORD_USER = "$2a$12$Tw/CXcVmA09y0e7A.h3tzu8VDDpwiSfpoGQxXTVX5UirKCsEga/gq"; 

    private static final String PASSWORD_ADMIN = "$2a$12$sfmceDRaaF5MKFQeV1LP7.iJuFd.dteTrCw0qw31fM6R54pkqvz5K";

    private static final Timestamp CREATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Boolean IS_DELETED = false;

	@Override
	public void run(String... args) throws Exception {
        this.saveRole();
		this.saveUsers();
	}

    private void saveRole(){
        if(roleRepository.count() == 0){
            Role newRoleUser = new Role();
            newRoleUser.setName("USER");
            newRoleUser.setDescription("Usuario común logueado");
            newRoleUser.setTimestamp(CREATED_AT);
            roleRepository.save(newRoleUser);

            Role newRoleAdmin = new Role();
            newRoleAdmin.setName("ADMIN");
            newRoleAdmin.setDescription("Usuario administrador con acceso total");
            newRoleAdmin.setTimestamp(CREATED_AT);
            roleRepository.save(newRoleAdmin);
        }
    }

	private void saveUsers() {
		if (userRepository.count() == 0) {

            Role roleUser = roleRepository.findByName("USER");
            Role roleAdmin = roleRepository.findByName("ADMIN");

			User user1 = new User("Alejandro", "Iglesias", "alejandroIglesias@example.com", PASSWORD_USER, "http://www.google.com/image/alejandro", roleUser, CREATED_AT, IS_DELETED);
			User user2 = new User("Yassine", "Bustamante", "yassine@example.com", PASSWORD_USER, "http://www.google.com/image/yassine", roleUser, CREATED_AT, IS_DELETED);
			User user3 = new User("Delia", "Serna", "delia@example.com", PASSWORD_USER, "http://www.google.com/image/delia", roleUser, CREATED_AT, IS_DELETED);
			User user4 = new User("Adrian", "Moreno", "adrian@example.com", PASSWORD_USER, "http://www.google.com/image/adrian", roleUser, CREATED_AT, IS_DELETED);
			User user5 = new User("Jairo", "Riquelme", "jairo@example.com", PASSWORD_USER, "http://www.google.com/image/jairo", roleUser, CREATED_AT, IS_DELETED);
            User user6 = new User("Nelson", "Ochoa", "nelson@example.com", PASSWORD_USER, "http://www.google.com/image/nelson", roleUser, CREATED_AT, IS_DELETED);
            User user7 = new User("Adolfo", "Barroso", "adolfo@example.com", PASSWORD_USER, "http://www.google.com/image/adolfo", roleUser, CREATED_AT, IS_DELETED);
            User user8 = new User("Sara", "Puig", "sara@example.com", PASSWORD_USER, "http://www.google.com/image/sara", roleUser, CREATED_AT, IS_DELETED);
            User user9 = new User("Teofilo", "Manrique", "teofilo@example.com", PASSWORD_USER, "http://www.google.com/image/teofilo", roleUser, CREATED_AT, IS_DELETED);
            User user10 = new User("Marisa", "Camara", "marisa@example.com", PASSWORD_USER, "http://www.google.com/image/marisa", roleUser, CREATED_AT, IS_DELETED);

            User admin1 = new User("Delia", "Cobo", "deila@example.com", PASSWORD_ADMIN, "http://www.google.com/image/deila", roleAdmin, CREATED_AT, IS_DELETED);
			User admin2 = new User("Hayat", "Bermudez", "hayat@example.com", PASSWORD_ADMIN, "http://www.google.com/image/hayat", roleAdmin, CREATED_AT, IS_DELETED);
			User admin3 = new User("Francisco", "Oviedo", "francisco@example.com", PASSWORD_ADMIN, "http://www.google.com/image/francisco", roleAdmin, CREATED_AT, IS_DELETED);
			User admin4 = new User("Facundo", "Perez", "facundo@example.com", PASSWORD_ADMIN, "http://www.google.com/image/facundo", roleAdmin, CREATED_AT, IS_DELETED);
			User admin5 = new User("Lautaro", "Ortigoza", "lautaro@example.com", PASSWORD_ADMIN, "http://www.google.com/image/lautaro", roleAdmin, CREATED_AT, IS_DELETED);
            User admin6 = new User("Alexis", "Ramirez", "alexis@example.com", PASSWORD_ADMIN, "http://www.google.com/image/alexis", roleAdmin, CREATED_AT, IS_DELETED);
            User admin7 = new User("Ignacio", "Gomez", "ignacio@example.com", PASSWORD_ADMIN, "http://www.google.com/image/ignacio", roleAdmin, CREATED_AT, IS_DELETED);
            User admin8 = new User("Ezequiel", "Ruiz", "ezequiel@example.com", PASSWORD_ADMIN, "http://www.google.com/image/ezequiel", roleAdmin, CREATED_AT, IS_DELETED);
            User admin9 = new User("Agustin", "Bermudez", "agustin@example.com", PASSWORD_ADMIN, "http://www.google.com/image/agustin", roleAdmin, CREATED_AT, IS_DELETED);
            User admin10 = new User("Esteban", "Lopez", "esteban@example.com", PASSWORD_ADMIN, "http://www.google.com/image/esteban", roleAdmin, CREATED_AT, IS_DELETED);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(user9);
            userRepository.save(user10);
            userRepository.save(admin1);
            userRepository.save(admin2);
            userRepository.save(admin3);
            userRepository.save(admin4);
            userRepository.save(admin5);
            userRepository.save(admin6);
            userRepository.save(admin7);
            userRepository.save(admin8);
            userRepository.save(admin9);
            userRepository.save(admin10);

		}
	}
	
}