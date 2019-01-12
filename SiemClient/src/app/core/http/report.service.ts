import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';

import { ReportRequest } from '../../shared/model/report-request.model';
import { TokenUtilsService } from '../util/token-utils.service';
import { RestService } from './rest.service';

@Injectable()
export class ReportService extends RestService<Blob>{

  constructor(protected http: HttpClient,
    toastr: ToastrService,
    private tokenUtils: TokenUtilsService) {
    super(http, '/api/reports', toastr)
  }

  downloadReport(reportRequest: ReportRequest): Observable<Blob> {
    reportRequest = reportRequest.getFormatted();
    return this.http.post(`${this.baseUrl}/generate`, reportRequest, {
      headers: new HttpHeaders({
        'Accept': '*/*, application/pdf, application/json'
      }),
      responseType: 'blob'
    }).pipe(
      catchError(this.handleError<Blob>())
    );
  }

}
