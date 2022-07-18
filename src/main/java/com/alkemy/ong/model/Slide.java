package com.alkemy.ong.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.alkemy.ong.model.Organization;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "slides")
@Getter
@Setter
public class Slide {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String text;
    private Integer order;

    @ManyToOne
	@JoinColumn(name = "organization_id", referencedColumnName = "id_organization" )
    private Organization organization;

}
