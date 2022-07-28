package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestimonialDto {
    private String  id;
    @NotBlank
    private String name;
    private String image;
    @NotBlank
    private String content;
    private Timestamp timestamp;
    private Boolean softDelete;

}
