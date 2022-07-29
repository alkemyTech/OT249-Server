package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
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