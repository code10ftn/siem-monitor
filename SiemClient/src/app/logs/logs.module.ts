import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { SharedModule } from '../shared/shared.module';
import { LogsListComponent } from './logs-list/logs-list.component';

@NgModule({
  imports: [
    CommonModule,
    NgxDatatableModule,
    SharedModule
  ],
  declarations: [LogsListComponent],
  exports: [LogsListComponent]
})
export class LogsModule { }
