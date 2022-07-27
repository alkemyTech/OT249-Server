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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testimonials")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Testimonial {
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String  id;
	
	@Column(nullable = false)
	private String name;
	private String image;
	private String content;
	private Timestamp timestamp = Timestamp.from(Instant.now());
	private Boolean softDelte = Boolean.FALSE;
	
	public Testimonial(String name, String image, String content, Timestamp timestamp, Boolean softDelte) {
		this.name = name;
		this.image = image;
		this.content = content;
		this.timestamp = timestamp;
		this.softDelte = softDelte;
	}

	
}
