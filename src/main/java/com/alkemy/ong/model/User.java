package com.alkemy.ong.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;	
	
	@Id
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
	@JoinColumn(name = "rol_id", referencedColumnName = "id" )
	private Role role;
	
	private Timestamp timestamp; 
	
	private boolean deleted = Boolean.FALSE;
	
	public User() {
	}

	public User(UUID id ,String firstName, String lastName, String email, String password, String photo, Role rol, Timestamp timestamp, boolean deleted) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.photo = photo;
		this.role = rol;
		this.timestamp = timestamp;
		this.deleted = deleted;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(this.getRole());
	}



	@Override
	public String getUsername() {

		return this.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {

		return !this.deleted;
	}

	@Override
	public boolean isAccountNonLocked() {

		return !this.deleted;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return !this.deleted;
	}

	@Override
	public boolean isEnabled() {

		return !this.deleted;
	}


}
