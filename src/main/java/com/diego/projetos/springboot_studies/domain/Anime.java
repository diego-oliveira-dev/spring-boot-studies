package com.diego.projetos.springboot_studies.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    // 2nd way: using Spring Boot Validation Dependency.
    // @NotNull -> doesn't allow for null
    // @NotEmpty -> doesn't allow for empty fields or null
    // @NotBlank -> @NotEmpty and @NotNull on roids:
    // it doesn't allow for null, empty fields or whitespace
    @NotBlank(message = "Anime name cannot be empty")
    private String name;
    // @JsonProperty("name")
    // private String animeName;
    // This way, Jackson maps 'animeName' as 'name'

}