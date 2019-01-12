import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';

import { RuleService } from '../../core/http/rule.service';
import { ConfirmModalComponent } from '../../shared/confirm-modal/confirm-modal.component';
import { Page } from '../../shared/model/page.model';
import { Rule } from '../../shared/model/rule.model';

@Component({
  selector: 'siem-rules-list',
  templateUrl: './rules-list.component.html',
  styleUrls: ['./rules-list.component.css']
})
export class RulesListComponent implements OnInit {

  rows: Rule[];

  page = new Page();

  modalRef: BsModalRef;

  constructor(private modalService: BsModalService,
    private ruleService: RuleService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.setPage({ offset: 0 });
  }

  setPage(pageInfo) {
    this.page.pageNumber = pageInfo.offset;
    this.getRules();
  }

  getRules() {
    this.ruleService.findAllPageable(this.page).subscribe(
      pagedData => {
        this.page.setPagedData(pagedData);
        this.rows = pagedData.content;
      },
      err => { });
  }

  showRuleDetails(rule: Rule) {
    this.router.navigate(['rule-details', rule.id]);
  }

  openConfirmDialog(row) {
    this.modalRef = this.modalService.show(ConfirmModalComponent);
    this.modalRef.content.onClose.subscribe(result => {
      if (result) {
        this.deleteRule(row);
      }
    });
  }

  deleteRule(rule: Rule) {
    this.ruleService.delete(rule.id).subscribe(
      res => {
        this.rows = this.rows.filter(item => item !== rule);
        this.page.totalElements--;
      }, err => { });
  }
}
