package com.alkemy.ong.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Table(name = "members")
@Entity
@Data
public class Member {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(nullable = false)
	private UUID id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(name = "facebook_url")
	private String facebookUrl;
	
	@Column(name = "instagram_url")
	private String instagramUrl;
	
	@Column(name = "linkedin_url")
	private String linkedinUrl;
	
	@Column(nullable = false)
	private String image;
	
	private String description;
	
	private Timestamp timestamp;
	
	private Boolean isDelete;
	

}
