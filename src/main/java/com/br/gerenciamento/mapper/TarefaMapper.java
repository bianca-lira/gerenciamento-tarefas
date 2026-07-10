package com.br.gerenciamento.mapper;

import com.br.gerenciamento.dto.TarefaDto;
import com.br.gerenciamento.entities.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    @Mapping(target = "status", expression = "java(tarefa.getStatus() != null ? tarefa.getStatus().name() : null)")
    TarefaDto toDto(Tarefa tarefa);

    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? com.br.gerenciamento.enums.StatusEnum.valueOf(dto.getStatus()) : null)")
    Tarefa toEntity(TarefaDto dto);

    List<Tarefa> toEntityList(List<TarefaDto> dtoList);

    List<TarefaDto> toDtoList(List<Tarefa> entityList);


}
