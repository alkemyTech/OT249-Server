package com.alkemy.ong.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "categories")
@Entity
@Data
public class Category {
    private String name;
    private String description;
    private String image;
}
