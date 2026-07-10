package com.br.gerenciamento.repository;

import com.br.gerenciamento.entities.Tarefa;
import com.br.gerenciamento.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByTituloAndStatus(String titulo, StatusEnum statusEnum);

    Page<Tarefa> findByStatus(StatusEnum status, Pageable pageable);

    Page<Tarefa> findByResponsavelContainingIgnoreCase(String responsavel, Pageable pageable);

    Page<Tarefa> findAll(Pageable pageable);

    Page<Tarefa> findByStatusAndResponsavelContainingIgnoreCase(
            StatusEnum status,
            String responsavel,
            Pageable pageable);
}
