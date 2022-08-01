package com.alkemy.ong.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "members")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE members SET is_delete = true WHERE id=?")
@Where(clause = "is_delete=false")
public class Member {
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String  id;
	
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
	
	@Column(name = "is_delete")
	private Boolean isDelete= false;

	public Member(String name, String facebookUrl, String instagramUrl, String linkedinUrl, String image,
			String description, Timestamp timestamp, Boolean isDelete) {
		this.name = name;
		this.facebookUrl = facebookUrl;
		this.instagramUrl = instagramUrl;
		this.linkedinUrl = linkedinUrl;
		this.image = image;
		this.description = description;
		this.timestamp = timestamp;
		this.isDelete = isDelete;
	}
	
	

}
