package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCommentDto {
	@NotBlank
	private String body;
	@NotNull
	private String user;
	@NotNull
	private String news;
}
