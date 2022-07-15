package com.alkemy.ong.model;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testimonials")
@Getter @Setter @NoArgsConstructor
public class Testimonial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTestimonial;
	
	@Column(nullable = false)
	private String name;
	private String image;
	private String content;
	private Timestamp timestamp = Timestamp.from(Instant.now());;
	private Boolean softDelte = Boolean.FALSE;
}
