package com.alkemy.ong.model;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "roles")
@Entity
@Data
public class Role {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(nullable = false,name = "id", columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private Set<User> users;

	private String description;

	private Timestamp timestamp;
}
