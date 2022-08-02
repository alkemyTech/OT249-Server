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
	private String name;
	@NotBlank
	private String content;
	@NotBlank
	private String image;
	
	@NotNull
	private String idCategory;
}
