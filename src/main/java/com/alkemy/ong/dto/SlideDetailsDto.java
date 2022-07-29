package com.alkemy.ong.dto;

import com.alkemy.ong.model.Organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlideDetailsDto {

    private String  id;
    private String imageUrl;
    private String text;
    private Integer position;
    private Organization organization;
}
