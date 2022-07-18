package com.alkemy.ong.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Table(name = "roles")
@Entity
@Data
public class Role {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private private Set<User> users;

	private String description;

	private Timestamp timestamp;
}