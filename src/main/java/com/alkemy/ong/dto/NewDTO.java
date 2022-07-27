package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewDTO {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String content;
    @NotBlank
    private String image;
    private LocalDateTime timestamp;
    private CategoryDto category;
    private boolean softDelete;

}
