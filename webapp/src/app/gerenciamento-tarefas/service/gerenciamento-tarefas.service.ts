import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Tarefa } from '../model/tarefa';
import { Observable } from 'rxjs';
import { Page } from '../model/page';

@Injectable({
  providedIn: 'root'
})
export class GerenciamentoTarefasService {
  private API = 'http://localhost:8080/api/tarefas';

  constructor(private http: HttpClient) {}

  
  getAll(page: number, size: number, status?: string, responsavel?: string): Observable<Page<Tarefa>> {
    let params = new HttpParams() 
      .set('page', page) 
      .set('size', size) 
      .set('sort', 'dataCriacao');

    if (status) { 
      params = params.set('status', status); 
    } 
    
    if (responsavel) { 
      params = params.set('responsavel', responsavel); 
    } 
    
    return this.http.get<Page<Tarefa>>(this.API, { params });
  }

  save(tarefa: Tarefa): Observable<Tarefa> {
      return this.http.post<Tarefa>(
        `${this.API}/save`,
        tarefa
      );
  }

  getById(id: number): Observable<Tarefa> { 
    return this.http.get<Tarefa>(`${this.API}/${id}`); 
  }

  update(tarefa: Tarefa): Observable<Tarefa> { 
    return this.http.put<Tarefa>(`${this.API}`, tarefa); 
  } 
  
  delete(id: number): Observable<void> { 
    return this.http.delete<void>(`${this.API}/${id}`); 
  }
}
