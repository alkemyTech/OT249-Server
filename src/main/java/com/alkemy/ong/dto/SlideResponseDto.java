package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlideResponseDto {

    private String  id;
    private String imageUrl;
    private String text;
    private Integer position;
    private PublicOrganizationDto publicOrganizationDto;
}
