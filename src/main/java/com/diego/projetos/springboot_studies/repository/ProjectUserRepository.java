package com.diego.projetos.springboot_studies.repository;

import com.diego.projetos.springboot_studies.domain.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    ProjectUser findByUsername(String username);
}
