package com.diego.projetos.springboot_studies.service;

import com.diego.projetos.springboot_studies.repository.ProjectUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProjectUserDetailsService implements UserDetailsService {

    private final ProjectUserRepository projectUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user: {}", username);
        return Optional.ofNullable(projectUserRepository.findByUsername(username))
                .orElseThrow(() ->  new UsernameNotFoundException("Project User not found"));
    }
}
