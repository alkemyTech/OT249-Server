package com.alkemy.ong.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PublicOrganizationDto {
        @NotBlank
        private String name;
        @NotBlank
        private String image;
        @NotBlank
        private Integer phone;
        @NotBlank
        private String address;
}
