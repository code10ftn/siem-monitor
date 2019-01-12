import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';
import { GetReportComponent } from './get-report/get-report.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule
  ],
  declarations: [GetReportComponent]
})
export class ReportsModule { }
