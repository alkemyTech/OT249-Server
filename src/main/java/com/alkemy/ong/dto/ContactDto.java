package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContactDto {

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    private String phone;
    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "Email incorrecto")
    private String email;
    private String message;
}
