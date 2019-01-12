import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';

import { LogItem } from '../../shared/model/log-item.model';
import { LogQuery } from '../../shared/model/log-query.model';
import { Page } from '../../shared/model/page.model';
import { PagedData } from '../../shared/model/paged-data.model';
import { RestService } from './rest.service';

@Injectable()
export class LogService extends RestService<LogItem> {

  constructor(protected http: HttpClient,
    toastr: ToastrService) {
    super(http, '/api/logs', toastr);
  }

  search(query: LogQuery, page: Page): Observable<PagedData<LogItem>> {
    query = query.getFormatted();
    return this.http.post<PagedData<LogItem>>(
      `${this.baseUrl}/search`, query, { params: { 'page': String(page.pageNumber), 'size': String(page.size) } }).pipe(
        catchError(this.handleError<PagedData<LogItem>>())
      );
  }
}
