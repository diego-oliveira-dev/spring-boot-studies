package com.diego.projetos.springboot_studies.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // junction of @ToString, @EqualsAndHashCode, @Getter/@Setter and @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor // generate an empty constructor, necessary for using @Entity (JPA pattern)
@Entity // specifies that this class maps to a db table
@Builder
public class Anime {
    @Id // specifies that id is used as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // used alongside @Id to make id auto-generated
    // search for generation types later
    private Long id;

    // how to make a field not nullable (or add any kind of validation):
    // 1st way: using @Column(nullable = false), but this way only works for
    // future POSTs, and it doesn't update the database
    // 2nd way: using Spring Boot Validation Dependency. Since you're not using
    // directly this class in Controller layer, you must add @NotNull and/or
    // @NotEmpty to your DTOs
    private String name;
    // @JsonProperty("name")
    // private String animeName;
    // This way, Jackson maps 'animeName' as 'name'

}