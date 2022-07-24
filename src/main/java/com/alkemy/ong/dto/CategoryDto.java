package com.alkemy.ong.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	@NotBlank
	private String name;
    
	private String description;
    
	private String image;
	
}
