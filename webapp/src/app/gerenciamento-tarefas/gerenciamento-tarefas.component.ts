import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Tarefa } from './model/tarefa';
import { GerenciamentoTarefasService } from './service/gerenciamento-tarefas.service';

@Component({
  selector: 'app-gerenciamento-tarefas',
  templateUrl: './gerenciamento-tarefas.component.html',
  styleUrls: ['./gerenciamento-tarefas.component.css']
})
export class GerenciamentoTarefasComponent implements OnInit {
  title = 'webapp';
  tarefas: Tarefa[] = [];
  totalRecords = 0;
  novaTarefa: boolean = false;
  tarefa = new Tarefa();
  statusList = [
    { label: 'Pendente', value: 'PENDENTE' },
    { label: 'Em Andamento', value: 'EM_ANDAMENTO' },
    { label: 'Concluida', value: 'CONCLUIDA' }
    ];
  mensagemErro: string = '';
  mensagemSucesso: string = '';
  isShowSuccess: boolean = false;
  isShowErro: boolean = false;
  loading: boolean = true;
  filtro = {
    status: '',
    responsavel: ''
  };

   constructor(private gerenciamentoTarefasService: GerenciamentoTarefasService) {}

   ngOnInit() {
        this.carregarTarefas();
    }

    carregarTarefas(page: number = 0){
        this.loading = true;
        this.gerenciamentoTarefasService.getAll(page, 10,this.filtro.status, this.filtro.responsavel)
        .subscribe({
            next: (r) => {
                this.tarefas = r.content;
                this.totalRecords = r.totalElements;
                this.loading = false;
            },
            error: (erro) => { 
                this.isShowErro = true;
                this.loading = false;
                this.showErro('Erro ao buscar tarefas:' + erro.error.message);
            }
        });
    }

    loadData(event: any) {
        const page = event.first / event.rows;
        const size = event.rows;

        this.gerenciamentoTarefasService.getAll(page, size)
            .subscribe(response => {
            this.tarefas = response.content;
            this.totalRecords = response.totalElements;
        });
    }

    criarTarefa(){
        this.loading = true;
        if (!this.tarefa.titulo.trim()) {
            this.loading = false;
            return;
        }

        if(this.tarefa.id){
            this.atualizarTarefa(this.tarefa);
        } else {
            this.gerenciamentoTarefasService.save(this.tarefa).subscribe({
                next: () => {
                    this.loading = false;
                    this.isShowSuccess = true;
                    this.showSuccess("Tarefa salva com sucesso.");
                    this.novaTarefa = false;
                    this.carregarTarefas();
                    this.limparFormulario();
                },
                error: (erro) => {
                    this.showErro('Erro ao criar tarefa:' +  erro.error.message);
                    this.loading = false;
                }
            });
        }
    }
    
    editarTarefa(task: Tarefa) {
        this.tarefa = { ...task };
        this.novaTarefa = true;
    }

    atualizarTarefa(tarefa: Tarefa): void {
        this.loading = true;
        this.gerenciamentoTarefasService.update(tarefa).subscribe({
        next: () => {
            this.isShowSuccess = true;
            this.carregarTarefas();
            this.showSuccess("Status atualizado.");
            this.loading = false;
            this.novaTarefa = false;
            this.carregarTarefas();
            this.limparFormulario();
        },
        error: (erro) => {
            this.showErro('Erro ao atualizar tarefa:' +  erro.error.message);
            this.loading = false;
        }
        });
    }

    openDialogCreate(){
        if(this.novaTarefa){
            this.novaTarefa = false;
        } else {
            this.novaTarefa = true;
        }
    }

    removerTarefa(id: number): void {
        this.loading = true;
        this.gerenciamentoTarefasService.delete(id).subscribe({
        next: () => {
            this.carregarTarefas();
            this.isShowSuccess = true;
            this.showSuccess("Tarefa removida");
            this.loading = false;
        },
        error: (erro) => {
            this.showErro( erro.error.message );
            this.loading = false;
        }
        });
    }


    limparFormulario(): void {
        this.tarefa = new Tarefa();
    }

    showErro(mensagem: string): void {
        this.isShowErro = true;
        this.mensagemErro = mensagem;
        this.mensagemSucesso = '';

        setTimeout(() => {this.mensagemErro = ''; }, 3000);
    }

    showSuccess(mensagem: string): void {
        this.mensagemSucesso = mensagem;
        this.mensagemErro = '';

        setTimeout(() => { this.mensagemSucesso = ''; }, 3000);
        this.isShowSuccess = false;
    }

    limparFiltros(): void {
        this.filtro = {
            status: '',
            responsavel: ''
        };

        this.carregarTarefas();
    }

}
