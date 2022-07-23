package com.alkemy.ong.dto;

import com.alkemy.ong.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewDTO {

    private String id;
    private String name;
    private String content;
    private String image;
    private LocalDateTime timestamp;
    private Category category;
    private boolean softDelete;

}
