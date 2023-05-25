package com.latam.alura.foro.Repositories;

import com.latam.alura.foro.Domains.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
