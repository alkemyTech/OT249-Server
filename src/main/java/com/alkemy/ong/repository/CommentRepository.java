package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String>{

}