package com.alkemy.ong.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String  id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private String image;
    private LocalDateTime timestamp;
    @ManyToOne
    private Category category;
    
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    private Set<Comment> comments;
    
    private boolean softDelete;
    
    public News(String name, String content, String image, LocalDateTime timestamp, Category category,
            boolean softDelete) {
        this.name = name;
        this.content = content;
        this.image = image;
        this.timestamp = timestamp;
        this.category = category;
        this.softDelete = softDelete;
    }

    
}
