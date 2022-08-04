package com.alkemy.ong.Utils;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.IRoleService;

@Component
public class UserSeedData implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private IRoleService roleService;
	
	@Override
	public void run(String... args) throws Exception {
        this.saveRole();
		this.saveUsers();
	}

    private void saveRole(){
        if(roleRepository.count() == 0){
            Role roleUser = new Role();
            roleUser.setName("USER");
            roleUser.setDescription("Usuario común logueado");
            roleUser.setTimestamp(new Timestamp(System.currentTimeMillis()));
            roleRepository.save(roleUser);

            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleAdmin.setDescription("Usuario administrador con acceso total");
            roleAdmin.setTimestamp(new Timestamp(System.currentTimeMillis()));
            roleRepository.save(roleAdmin);
        }
    }

	private void saveUsers() {
		if (userRepository.count() == 0) {
			User user1 = new User("Alejandro", "Iglesias", "alejandroIglesias@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/alejandro", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
			User user2 = new User("Yassine", "Bustamante", "yassine@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/yassine", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
			User user3 = new User("Delia", "Serna", "delia@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/delia", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
			User user4 = new User("Adrian", "Moreno", "adrian@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/adrian", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
			User user5 = new User("Jairo", "Riquelme", "jairo@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/jairo", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
            User user6 = new User("Nelson", "Ochoa", "nelson@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/nelson", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
            User user7 = new User("Adolfo", "Barroso", "adolfo@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/adolfo", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
            User user8 = new User("Sara", "Puig", "sara@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/sara", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
            User user9 = new User("Teofilo", "Manrique", "teofilo@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/teofilo", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);
            User user10 = new User("Marisa", "Camara", "marisa@example.com", passwordEncoder.encode("1234"), "http://www.google.com/image/marisa", roleService.getRoleByName("USER"), new Timestamp(System.currentTimeMillis()), false);

            User admin1 = new User("Delia", "Cobo", "deila@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/deila", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
			User admin2 = new User("Hayat", "Bermudez", "hayat@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/hayat", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
			User admin3 = new User("Francisco", "Oviedo", "francisco@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/francisco", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
			User admin4 = new User("Facundo", "Perez", "facundo@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/facundo", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
			User admin5 = new User("Lautaro", "Ortigoza", "lautaro@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/lautaro", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
            User admin6 = new User("Alexis", "Ramirez", "alexis@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/alexis", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
            User admin7 = new User("Ignacio", "Gomez", "ignacio@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/ignacio", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
            User admin8 = new User("Ezequiel", "Ruiz", "ezequiel@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/ezequiel", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
            User admin9 = new User("Agustin", "Bermudez", "agustin@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/agustin", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);
            User admin10 = new User("Esteban", "Lopez", "esteban@example.com", passwordEncoder.encode("admin"), "http://www.google.com/image/esteban", roleService.getRoleByName("ADMIN"), new Timestamp(System.currentTimeMillis()), false);

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