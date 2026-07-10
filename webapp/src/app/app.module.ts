import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { GerenciamentoTarefasComponent } from './gerenciamento-tarefas/gerenciamento-tarefas.component';
import { MenuModule } from 'primeng/menu';
import { BadgeModule } from 'primeng/badge';
import { RippleModule } from 'primeng/ripple';
import { TableModule  } from 'primeng/table';
import { AppRoutingModule } from './app.routing.module';
import { HttpClientModule } from '@angular/common/http';
import { ButtonModule } from "primeng/button";
import { DialogModule } from "primeng/dialog";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@NgModule({
  declarations: [
    AppComponent,
    GerenciamentoTarefasComponent
  ],
  imports: [
    BrowserModule,
    MenuModule,
    BadgeModule,
    RippleModule,
    AppRoutingModule,
    TableModule,
    HttpClientModule,
    ButtonModule,
    DialogModule,
    BrowserAnimationsModule,
    FormsModule,
    DropdownModule,
    ProgressSpinnerModule
],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
