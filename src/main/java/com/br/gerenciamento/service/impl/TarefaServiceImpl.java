package com.br.gerenciamento.service.impl;

import com.br.gerenciamento.entities.Tarefa;
import com.br.gerenciamento.enums.StatusEnum;
import com.br.gerenciamento.exception.GlobalExceptionHandler;
import com.br.gerenciamento.exception.ResourceNotFoundException;
import com.br.gerenciamento.repository.TarefaRepository;
import com.br.gerenciamento.service.TarefaService;
import com.br.gerenciamento.dto.TarefaDto;
import com.br.gerenciamento.mapper.TarefaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaMapper tarefaMapper;

    @Override
    public Page<TarefaDto> getAll(String status, String responsavel, Integer page, Integer size, String sort) {
        Page<Tarefa> tarefas;
        Pageable pageable = PageRequest.of(page, size);

        boolean possuiStatus = status != null && !status.isBlank();
        boolean possuiResponsavel = responsavel != null && !responsavel.isBlank();
        if (possuiStatus && possuiResponsavel) {
            tarefas = tarefaRepository.findByStatusAndResponsavelContainingIgnoreCase(
                    StatusEnum.valueOf(status.toUpperCase()),
                    responsavel,
                    pageable);

        } else if (possuiStatus) {
            tarefas = tarefaRepository.findByStatus(
                    StatusEnum.valueOf(status.toUpperCase()),
                    pageable);

        } else if (possuiResponsavel) {
            tarefas = tarefaRepository.findByResponsavelContainingIgnoreCase(
                    responsavel,
                    pageable);

        } else {
            tarefas = tarefaRepository.findAll(pageable);
        }

        return tarefas.map(tarefaMapper::toDto);
    }

    @Override
    public TarefaDto getById(Long id) throws Exception {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tarefa não encontrada."));

        return tarefaMapper.toDto(tarefa);
    }

    @Override
    public void save(TarefaDto dto) throws Exception {
        try {
            validarTarefa(dto);

            Tarefa tarefa = tarefaMapper.toEntity(dto);
            tarefa.setDataDeCriacao(LocalDateTime.now());
            tarefa.setStatus(StatusEnum.valueOf(dto.getStatus()));
            if (tarefa.getStatus().equals(StatusEnum.CONCLUIDA)) {
                tarefa.setDataDeConclusao(LocalDateTime.now());
            }
            tarefaRepository.save(tarefa);
        } catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public void update(TarefaDto dto) throws Exception {
        TarefaDto tarefa = this.getById(dto.getId());

        tarefa.setDescricao(dto.getDescricao());
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setStatus(dto.getStatus());

        if(dto.getStatus().equals(StatusEnum.CONCLUIDA.name())
                && tarefa.getDataDeConclusao() == null){
            tarefa.setDataDeConclusao(LocalDateTime.now().toString());
        } else {
            tarefa.setDataDeConclusao(null);
        }
        tarefa.setResponsavel(dto.getResponsavel());

        tarefaRepository.save(tarefaMapper.toEntity(tarefa));
    }

    @Override
    public void excluir(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tarefa não encontrada."));

        tarefaRepository.delete(tarefa);

    }

    private void validarTarefa(TarefaDto dto) throws Exception {
        //Validar pelo nome
        if(!tarefaRepository.findByTituloAndStatus(dto.getTitulo(), StatusEnum.EM_ANDAMENTO).isEmpty()) {
            throw new Exception("Já existe uma tarefa em andamento com o mesmo titulo.");
        }

        if(dto.getTitulo() == null){
            throw new Exception("Título é obrigatório.");
        }

        if(dto.getDescricao() == null){
            throw new Exception("Descrição é obrigatório.");
        }

        if(dto.getResponsavel() == null){
            throw new Exception("Responsável é obrigatório.");
        }
    }
}
