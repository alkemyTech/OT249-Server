package com.alkemy.ong.dto;


import javax.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityDto {
	
	@NotBlank
    private String name;

	@NotBlank
    private String content;

    private String image;
	
}
