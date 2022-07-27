package com.alkemy.ong.dto;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {
    private String id;
    @NotBlank
    private String name;
    private String description;
    private String image;
    private Timestamp timestamp;
    private Boolean deleted;
}
