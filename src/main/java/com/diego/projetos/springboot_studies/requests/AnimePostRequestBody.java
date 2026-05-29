package com.diego.projetos.springboot_studies.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// basically a DTO
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimePostRequestBody {
    // @NotNull - doesn't allow for null
    // @NotEmpty -> doesn't allow for empty fields or null
    @NotBlank(message = "Anime name cannot be empty")
    // @NotBlank -> @NotEmpty and @NotNull on roids:
    // it doesn't allow for null, empty fields or whitespace
    private String name;
}
