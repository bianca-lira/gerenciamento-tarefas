package service.impl;

import com.br.gerenciamento.dto.TarefaDto;
import com.br.gerenciamento.entities.Tarefa;
import com.br.gerenciamento.enums.StatusEnum;
import com.br.gerenciamento.exception.ResourceNotFoundException;
import com.br.gerenciamento.mapper.TarefaMapper;
import com.br.gerenciamento.repository.TarefaRepository;
import com.br.gerenciamento.service.impl.TarefaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceImplTest {

    @Mock
    private TarefaRepository repository;

    @Mock
    private TarefaMapper mapper;

    @InjectMocks
    private TarefaServiceImpl service;

    private Tarefa tarefa;
    private TarefaDto dto;

    @BeforeEach
    void setup() {
        tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Teste");
        tarefa.setDescricao("Descrição");
        tarefa.setResponsavel("Bianca");
        tarefa.setStatus(StatusEnum.EM_ANDAMENTO);
        tarefa.setDataDeCriacao(LocalDateTime.now());

        dto = new TarefaDto();
        dto.setId(1L);
        dto.setTitulo("Teste");
        dto.setDescricao("Descrição");
        dto.setResponsavel("Bianca");
        dto.setStatus(StatusEnum.EM_ANDAMENTO.name());
    }

    @Test
    void deveBuscarTodasAsTarefas() {
        Page<Tarefa> page = new PageImpl<>(Collections.singletonList(tarefa));

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(any())).thenReturn(dto);

        Page<TarefaDto> resultado = service.getAll(null, null,0,10,"id");

        assertEquals(1, resultado.getTotalElements());

        verify(repository).findAll(any(Pageable.class));
        verify(mapper).toDto(any());
    }

    @Test
    void deveBuscarPorId() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(mapper.toDto(tarefa)).thenReturn(dto);

        TarefaDto retorno = service.getById(1L);

        assertNotNull(retorno);
        assertEquals("Teste", retorno.getTitulo());

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarId() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void deveSalvarTarefa() throws Exception {

        when(repository.findByTituloAndStatus(any(), any()))
                .thenReturn(Collections.emptyList());

        when(mapper.toEntity(dto)).thenReturn(tarefa);

        service.save(dto);

        verify(repository).save(any(Tarefa.class));
    }

    @Test
    void naoDeveSalvarTituloDuplicado() {
        when(repository.findByTituloAndStatus(any(), any()))
                .thenReturn(Collections.singletonList(tarefa));

        assertThrows(ResourceNotFoundException.class,
                () -> service.save(dto));

        verify(repository, never()).save(any());
    }

    @Test
    void deveAtualizarTarefa() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(mapper.toDto(any())).thenReturn(dto);
        when(mapper.toEntity(any())).thenReturn(tarefa);

        dto.setTitulo("Novo titulo");
        dto.setDescricao("Nova descrição");
        dto.setResponsavel("João");
        dto.setStatus(StatusEnum.CONCLUIDA.name());

        service.update(dto);

        verify(repository).save(any(Tarefa.class));
    }

    @Test
    void deveExcluirTarefa() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));

        service.excluir(1L);

        verify(repository).delete(tarefa);
    }

    @Test
    void deveLancarExcecaoAoExcluirInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.excluir(1L));

        verify(repository, never()).delete(any());
    }

    @Test
    void naoDeveSalvarSemTitulo() {
        dto.setTitulo(null);

        assertThrows(ResourceNotFoundException.class,
                () -> service.save(dto));
    }

    @Test
    void naoDeveSalvarSemDescricao() {
        dto.setDescricao(null);

        assertThrows(ResourceNotFoundException.class,
                () -> service.save(dto));
    }

    @Test
    void naoDeveSalvarSemResponsavel() {
        dto.setResponsavel(null);

        assertThrows(ResourceNotFoundException.class,
                () -> service.save(dto));
    }

}