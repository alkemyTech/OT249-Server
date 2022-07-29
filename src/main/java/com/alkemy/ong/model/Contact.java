package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Contact {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private Integer phone;
    private String email;
    private String message;
    private Boolean deleted;
}
