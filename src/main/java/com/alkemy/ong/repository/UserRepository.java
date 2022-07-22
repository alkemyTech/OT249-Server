package com.alkemy.ong.repository;

import com.alkemy.ong.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
	Optional<User> findByEmail(String email);
}
