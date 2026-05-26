package com.diego.projetos.springboot_studies.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // junction of @ToString, @EqualsAndHashCode, @Getter/@Setter and @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor // generate an empty constructor, necessary for using @Entity (JPA pattern)
@Entity // specifies that this class maps to a db table
public class Anime {
    @Id // specifies that id is used as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // used alongside @Id to make id auto-generated
    // search for generation types later
    private Long id;
    private String name;
//    @JsonProperty("name")
//    private String animeName;
//    This way, Jackson maps 'animeName' as 'name'

}