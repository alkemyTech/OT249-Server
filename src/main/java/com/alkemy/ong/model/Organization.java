package com.alkemy.ong.model;

import lombok.Getter;
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
public class Organization {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    private String address;
    private Integer phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "welcome_text",nullable = false)
    private String welcomeText;

    @Column(name = "about_us_text")
    private String aboutUsText;

    private Timestamp timestamp = Timestamp.from(Instant.now());

    private Boolean deleted = Boolean.FALSE;

}


