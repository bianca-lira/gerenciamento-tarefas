package com.br.gerenciamento.resource;

import com.br.gerenciamento.dto.TarefaDto;
import com.br.gerenciamento.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/tarefas", produces = MediaType.APPLICATION_JSON_VALUE)
public class TarefaResource {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<Page<TarefaDto>> getAll(
            @RequestParam(required = false) String status, @RequestParam(required = false) String responsavel,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataCriacao") String sort){
        return new ResponseEntity<>(tarefaService.getAll(status, responsavel, page, size, sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDto> buscarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(tarefaService.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody TarefaDto dto) throws Exception {
        tarefaService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody TarefaDto dto) throws Exception {
        tarefaService.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tarefaService.excluir(id);
        return ResponseEntity.noContent().build(); }
}
