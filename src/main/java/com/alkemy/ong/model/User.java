package com.alkemy.ong.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
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
	private Role rol;
	
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
		this.rol = rol;
		this.timestamp = timestamp;
		this.deleted = deleted;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(this.getRol());
	}

	public String getPassword() {
		return password;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}

	public UUID getId() {
		return id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setHoraRegistro(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
