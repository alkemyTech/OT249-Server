package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "testimonials")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE testimonials SET soft_deLete = true WHERE id=?")
@Where(clause = "soft_deLete=false")
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
	private Boolean softDelete = Boolean.FALSE;
	
	public Testimonial(String name, String image, String content, Timestamp timestamp, Boolean softDelete) {
		this.name = name;
		this.image = image;
		this.content = content;
		this.timestamp = timestamp;
		this.softDelete = softDelete;
	}

	
}
