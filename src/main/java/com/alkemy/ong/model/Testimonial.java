package com.alkemy.ong.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testimonials")
@Getter @Setter @NoArgsConstructor
public class Testimonial {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "idTestimonial", updatable = false, nullable = false)
	private UUID idTestimonial;
	
	@Column(nullable = false)
	private String name;
	private String image;
	private String content;
	private Timestamp timestamp = Timestamp.from(Instant.now());
	private Boolean softDelte = Boolean.FALSE;
}
