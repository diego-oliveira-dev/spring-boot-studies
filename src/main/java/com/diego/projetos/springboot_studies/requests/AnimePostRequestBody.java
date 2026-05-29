package com.diego.projetos.springboot_studies.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// basically a DTO
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimePostRequestBody {
    private String name;
}
