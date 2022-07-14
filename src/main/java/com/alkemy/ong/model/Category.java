package com.alkemy.ong.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "categories")
@Entity
@Data
public class Category {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String image;
}
