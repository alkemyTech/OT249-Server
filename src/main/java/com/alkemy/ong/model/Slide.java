package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "slides")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Slide {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String  id;

    private String imageUrl;
    private String text;
    private Integer position;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", referencedColumnName = "id" )
    private Organization organization;

    public Slide(String imageUrl, String text, Integer position, Organization organization) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.position = position;
        this.organization = organization;
    }

    
}