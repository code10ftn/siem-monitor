import { Component, OnInit } from '@angular/core';
import { saveAs } from 'file-saver';

import { ReportService } from '../../core/http/report.service';
import { ReportRequest } from '../../shared/model/report-request.model';

@Component({
  selector: 'siem-get-report',
  templateUrl: './get-report.component.html',
  styleUrls: ['./get-report.component.css']
})
export class GetReportComponent implements OnInit {

  reportRequest = new ReportRequest();

  constructor(private reportService: ReportService) { }

  ngOnInit() {
  }

  downloadReport() {
    this.reportService.downloadReport(this.reportRequest).subscribe(
      blob => {
        saveAs(blob, 'report.pdf');
      }, err => { });
  }

}
