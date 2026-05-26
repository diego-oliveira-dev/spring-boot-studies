package com.diego.projetos.springboot_studies.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // junction of @ToString, @EqualsAndHashCode, @Getter/@Setter and @RequiredArgsConstructor
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;
//    @JsonProperty("name")
//    private String animeName;
//    This way, Jackson maps 'animeName' as 'name'

}