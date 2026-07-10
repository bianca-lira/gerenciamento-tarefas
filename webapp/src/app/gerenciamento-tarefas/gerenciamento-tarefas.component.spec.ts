import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciamentoTarefasComponent } from './gerenciamento-tarefas.component';

describe('GerenciamentoTarefasComponent', () => {
  let component: GerenciamentoTarefasComponent;
  let fixture: ComponentFixture<GerenciamentoTarefasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GerenciamentoTarefasComponent]
    });
    fixture = TestBed.createComponent(GerenciamentoTarefasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
