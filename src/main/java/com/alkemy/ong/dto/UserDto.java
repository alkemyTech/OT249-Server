package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String photo;
	
	@NotBlank
	private RoleDto role;

	private boolean deleted;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of( this.getRole() );
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
