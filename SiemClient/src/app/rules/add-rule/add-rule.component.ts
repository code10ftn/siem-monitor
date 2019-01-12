import { Component, OnInit } from '@angular/core';
import { Rule } from '../../shared/model/rule.model';

@Component({
  selector: 'siem-add-rule',
  templateUrl: './add-rule.component.html',
  styleUrls: ['./add-rule.component.css']
})
export class AddRuleComponent implements OnInit {

  rule = new Rule();

  constructor() { }

  ngOnInit() {
    this.rule.interval = 10;
    this.rule.repetitions = 5;
  }
}
