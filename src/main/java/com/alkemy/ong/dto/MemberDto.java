package com.alkemy.ong.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MemberDto {

	private String id;
	@NotBlank
	private String name;
	private String facebookUrl;
	private String instagramUrl;
	private String linkedinUrl;
	private String image;
	private String description;
	private Timestamp timestamp;
	private Boolean isDelete;
}
