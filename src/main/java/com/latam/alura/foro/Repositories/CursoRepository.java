package com.latam.alura.foro.Repositories;

import com.latam.alura.foro.Domains.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Page<Curso> findByEstadoTrue(Pageable pageable);
    Optional<Curso> findByIdAndEstadoTrue(Long id);
}
