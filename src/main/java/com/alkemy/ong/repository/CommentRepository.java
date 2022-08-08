package com.alkemy.ong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String>{
	List<Comment> findAllByOrderByTimestampDesc();
	public List<Comment> findByNews(News news);
}
