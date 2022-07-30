package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.alkemy.ong.model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNewsDto {

	@NotBlank
	@NotNull
	private String name;
	@NotBlank
	@NotNull
	private String content;
	@NotBlank
	@NotNull
	private String image;
	
	@NotNull
	private Category category;
}
