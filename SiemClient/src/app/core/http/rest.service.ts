import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';

import { Page } from '../../shared/model/page.model';
import { PagedData } from '../../shared/model/paged-data.model';

@Injectable()
export class RestService<T> {

  constructor(protected http: HttpClient,
    protected baseUrl: string,
    private toastr: ToastrService) { }

  findById(id: number): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError<T>())
    );
  }

  findAllPageable(page: Page, ...queryParams: any[]): Observable<PagedData<T>> {
    return this.http.get<PagedData<T>>(
      this.baseUrl, { params: { 'page': String(page.pageNumber), 'size': String(page.size) } }
    ).pipe(
      catchError(this.handleError<PagedData<T>>())
    );
  }

  create<D>(body: T | D): Observable<number> {
    return this.http.post<number>(this.baseUrl, body).pipe(
      catchError(this.handleError<number>())
    );
  }

  update<D>(id: number, body: T | D): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}/${id}`, body).pipe(
      catchError(this.handleError<T>())
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(this.handleError<void>())
    );
  }

  protected handleError<E>(operation = 'operation', result?: E) {
    return (response: any): Observable<E> => {
      console.error(response);
      if (response.error) {
        this.toastr.error(response.error.error);
      } else {
        this.toastr.error('Client side error!');
      }
      return Observable.throw(result as E);
    };
  }
}
