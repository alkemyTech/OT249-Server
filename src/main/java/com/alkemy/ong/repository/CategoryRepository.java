package com.alkemy.ong.repository;

import com.alkemy.ong.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, UUID> {
}
