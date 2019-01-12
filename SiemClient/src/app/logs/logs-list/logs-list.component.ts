import { Component, OnInit } from '@angular/core';

import { LogService } from '../../core/http/log.service';
import { LogItem } from '../../shared/model/log-item.model';
import { LogQuery } from '../../shared/model/log-query.model';
import { Page } from '../../shared/model/page.model';

@Component({
  selector: 'siem-logs-list',
  templateUrl: './logs-list.component.html',
  styleUrls: ['./logs-list.component.css']
})
export class LogsListComponent implements OnInit {

  rows: LogItem[];

  logQuery = new LogQuery();

  page = new Page();

  constructor(private logService: LogService) { }

  ngOnInit() {
    this.setPage({ offset: 0 });
  }

  setPage(pageInfo) {
    this.page.pageNumber = pageInfo.offset;
    this.pagedSearch();
  }

  search() {
    this.page = new Page();
    this.pagedSearch();
  }

  pagedSearch() {
    this.logService.search(this.logQuery, this.page).subscribe(
      pagedData => {
        this.page.setPagedData(pagedData);
        this.rows = pagedData.content;
      },
      err => { });
  }

  reset() {
    this.logQuery = new LogQuery();
    this.setPage({ offset: 0 });
  }
}
