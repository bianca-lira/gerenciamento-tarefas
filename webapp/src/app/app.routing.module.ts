
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GerenciamentoTarefasComponent } from './gerenciamento-tarefas/gerenciamento-tarefas.component';

const routes: Routes = [
  {
    path: 'app',
    component: GerenciamentoTarefasComponent
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
