package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "categories")
@Entity
@Data
@AllArgsConstructor
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String  id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String image;
    private Timestamp timestamp;
    private Boolean deleted = false;
    public Category(String name, String description, String image, Timestamp timestamp, Boolean deleted) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.timestamp = timestamp;
        this.deleted = deleted;
    }

    
}
