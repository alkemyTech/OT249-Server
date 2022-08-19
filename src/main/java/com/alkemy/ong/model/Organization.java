package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String  id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    private String address;
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "welcome_text",nullable = false)
    private String welcomeText;

    @Column(name = "about_us_text")
    private String aboutUsText;

    private Timestamp timestamp = Timestamp.from(Instant.now());

    private Boolean deleted = Boolean.FALSE;
    
    private String urlFacebook;
    private String urlInstagram;
    private String urlLinkedin;

    public Organization(String name, String image, String address, String phone, String email, String welcomeText,
            String aboutUsText, Timestamp timestamp, Boolean deleted) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
        this.timestamp = timestamp;
        this.deleted = deleted;
    }

    

}


