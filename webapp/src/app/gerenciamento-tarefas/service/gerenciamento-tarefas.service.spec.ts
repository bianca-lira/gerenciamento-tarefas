import { TestBed } from '@angular/core/testing';

import { GerenciamentoTarefasService } from './gerenciamento-tarefas.service';

describe('GerenciamentoTarefasService', () => {
  let service: GerenciamentoTarefasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GerenciamentoTarefasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
