package com.br.gerenciamento.service;

import com.br.gerenciamento.dto.TarefaDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TarefaService {

    Page<TarefaDto> getAll(String status, String responsavel, Integer page, Integer size, String sort);

    TarefaDto getById(Long id) throws Exception;

    void save(TarefaDto dto) throws Exception;

    void update(TarefaDto dto) throws Exception;

    void excluir(Long id);
}
