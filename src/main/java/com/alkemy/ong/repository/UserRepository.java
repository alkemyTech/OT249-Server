package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByEmail(String email);

	@Query("from User u where u.id=:id")
	User findByUserId(@Param("id") UUID id);
}
