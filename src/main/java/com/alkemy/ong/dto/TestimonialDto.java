package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestimonialDto {
    private String  id;
    private String name;
    private String image;
    private String content;
    private Timestamp timestamp;
    private Boolean softDelete;

}
