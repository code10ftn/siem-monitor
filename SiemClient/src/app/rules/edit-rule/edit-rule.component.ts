import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { RuleService } from '../../core/http/rule.service';
import { Rule } from '../../shared/model/rule.model';

@Component({
  selector: 'siem-edit-rule',
  templateUrl: './edit-rule.component.html',
  styleUrls: ['./edit-rule.component.css']
})
export class EditRuleComponent implements OnInit {

  rule = new Rule();

  constructor(private ruleService: RuleService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.getRule(params['id']);
    });
  }

  getRule(id: number) {
    this.ruleService.findById(id).subscribe(
      res => {
        this.rule = res;
      },
      err => {
        this.router.navigateByUrl('rules-list');
      });
  }

}
