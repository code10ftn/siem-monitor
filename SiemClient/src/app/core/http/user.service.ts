import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';

import { ChangePasswordDto } from '../../shared/model/change-password-dto';
import { User } from '../../shared/model/user.model';
import { TokenUtilsService } from '../util/token-utils.service';
import { RestService } from './rest.service';

@Injectable()
export class UserService extends RestService<User> {

  constructor(protected http: HttpClient,
    toastr: ToastrService,
    private tokenUtils: TokenUtilsService) {
    super(http, '/api/users', toastr);
  }

  changePassword(id: number, body: ChangePasswordDto): Observable<number> {
    return this.http.put<number>(`${this.baseUrl}/change-password`, body).pipe(
      catchError(this.handleError<number>())
    );
  }
}
