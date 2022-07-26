package com.alkemy.ong.repository;

import com.alkemy.ong.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	@Query("from User u where u.id=:id")
	User findByUserId(@Param("id") UUID id);
}
