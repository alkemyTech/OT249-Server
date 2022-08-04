package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.NotNull;

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
	
	@NotNull
	private RoleDto role;

	private boolean deleted;

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of( this.getRole() );
	}

	@Override
	@JsonIgnore
	public String getUsername() {

		return this.getEmail();
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {

		return !this.deleted;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {

		return !this.deleted;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {

		return !this.deleted;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {

		return !this.deleted;
	}

	public static final class UserPagedDto extends UserDto{

		@Override
		@JsonIgnore
		public String getPassword() {

			return super.getPassword();
		}
	}
}
