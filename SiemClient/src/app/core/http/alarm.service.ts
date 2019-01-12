import { Injectable } from '@angular/core';
import { RestService } from './rest.service';
import { Alarm } from '../../shared/model/alarm.model';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { TokenUtilsService } from '../util/token-utils.service';

@Injectable()
export class AlarmService extends RestService<Alarm> {

  constructor(protected http: HttpClient,
    toastr: ToastrService,
    private tokenUtils: TokenUtilsService) {
    super(http, '/api/alarms', toastr);
  }

}
