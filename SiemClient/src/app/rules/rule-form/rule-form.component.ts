import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { AuthService } from '../../core/http/auth.service';
import { RuleService } from '../../core/http/rule.service';
import { CAN_GET_RULE } from '../../core/util/constants';
import { Rule } from '../../shared/model/rule.model';

@Component({
  selector: 'siem-rule-form',
  templateUrl: './rule-form.component.html',
  styleUrls: ['./rule-form.component.css']
})
export class RuleFormComponent implements OnInit {

  rule = new Rule();

  constructor(private authService: AuthService,
    private ruleService: RuleService,
    private router: Router,
    private toastr: ToastrService) { }

  @Input()
  set setRule(rule: Rule) {
    this.rule = rule;
  }

  ngOnInit() {
  }

  saveRule() {
    if (this.rule.id) {
      this.ruleService.update(this.rule.id, this.rule).subscribe(
        response => {
          this.toastr.success(`Successfully saved rule!`);

          if (this.authService.checkPermission(CAN_GET_RULE)) {
            this.router.navigateByUrl('rules-list');
          }
        },
        err => { });
    } else {
      this.ruleService.create(this.rule).subscribe(
        response => {
          this.toastr.success(`Successfully saved rule!`);

          if (this.authService.checkPermission(CAN_GET_RULE)) {
            this.router.navigateByUrl('rules-list');
          }
        },
        err => { });
    }
  }

}
