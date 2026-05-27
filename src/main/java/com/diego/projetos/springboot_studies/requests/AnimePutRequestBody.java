package com.diego.projetos.springboot_studies.requests;

import lombok.Data;

// AnimePutRequestBody is basically a DTO
@Data
public class AnimePutRequestBody {
    private Long id;
    private String name;
}
