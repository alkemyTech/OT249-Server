package com.alkemy.ong.model;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "slides")
@Getter
@Setter
public class Slide {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    private String imageUrl;
    private String text;
    private Integer position;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", referencedColumnName = "id" )
    private Organization organization;
}