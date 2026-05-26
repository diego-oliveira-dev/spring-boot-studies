package com.diego.projetos.springboot_studies.requests;

import lombok.Data;

// basically a DTO
@Data
public class AnimePutRequestBody {
    private Long id;
    private String name;
}
