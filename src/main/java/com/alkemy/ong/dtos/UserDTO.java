package com.alkemy.ong.dtos;

import com.alkemy.ong.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String firstName;

    private String lastName;

    private String email;
    @JsonIgnore
    private String password;

    private String photo;

    private Role rol;

    private Timestamp timestamp;
}
