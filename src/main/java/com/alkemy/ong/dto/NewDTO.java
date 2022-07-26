package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class NewDTO {

    private UUID id;
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
