import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

import { Rule } from '../../shared/model/rule.model';
import { RestService } from './rest.service';

@Injectable()
export class RuleService extends RestService<Rule> {

  constructor(protected http: HttpClient,
    toastr: ToastrService) {
    super(http, '/api/rules', toastr);
  }

}
