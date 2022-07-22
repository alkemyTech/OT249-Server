package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDTO implements Serializable {

    @Email(message = "Email no valido")
    private String email;

    @Size(message = "El campo debe tener un minimo de 6 caracteres", min = 6)
    private String password;
}
