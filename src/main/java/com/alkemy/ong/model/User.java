package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "Este campo no puede estar vacio")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Este campo no puede estar vacio")
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email(message = "Email no valido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacio")
    private String password;

    @NotBlank(message = "Este campo no puede estar vacio")
    @Column(nullable = false)
    private String photo;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Role role;

    private Timestamp timestamp;

    private boolean deleted = Boolean.FALSE;


}
