package com.diego.projetos.springboot_studies.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// AnimePutRequestBody is basically a DTO
@Data
public class AnimePutRequestBody {
    private Long id;
    @NotBlank(message = "Anime name cannot be empty")
    private String name;
}
