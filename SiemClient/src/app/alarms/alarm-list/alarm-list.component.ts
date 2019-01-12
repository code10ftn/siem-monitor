import { Component, OnInit } from '@angular/core';

import { AlarmService } from '../../core/http/alarm.service';
import { Alarm } from '../../shared/model/alarm.model';
import { Page } from '../../shared/model/page.model';

@Component({
  selector: 'siem-alarm-list',
  templateUrl: './alarm-list.component.html',
  styleUrls: ['./alarm-list.component.css']
})
export class AlarmListComponent implements OnInit {

  rows: Alarm[];

  page = new Page();

  selected = [];

  constructor(private alarmService: AlarmService) { }

  ngOnInit() {
    this.setPage({ offset: 0 });
  }

  setPage(pageInfo) {
    this.page.pageNumber = pageInfo.offset;
    this.getAlarms();
  }

  getAlarms() {
    this.alarmService.findAllPageable(this.page).subscribe(
      pagedData => {
        this.page.setPagedData(pagedData);
        this.rows = pagedData.content;
      },
      err => { });
  }
}
