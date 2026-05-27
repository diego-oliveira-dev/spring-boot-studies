package com.diego.projetos.springboot_studies.repository;

import com.diego.projetos.springboot_studies.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// repository layer extending JpaRepository<Class, Type>
// Class -> class that represents the repository
// Type -> type that represents the @Id
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByName(String name);
}
