package com.alkemy.ong.repository;

import com.alkemy.ong.model.Category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, String> {
	@Query(value="from Category c where c.id=:id", nativeQuery = true)
	Category getById(@Param("id") String id);
	
	Optional<Category> findByName(String id);
}
