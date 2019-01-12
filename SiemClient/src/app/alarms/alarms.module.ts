import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { SharedModule } from '../shared/shared.module';
import { AlarmListComponent } from './alarm-list/alarm-list.component';

@NgModule({
    imports: [
        CommonModule,
        NgxDatatableModule,
        SharedModule
    ],
    declarations: [
        AlarmListComponent
    ],
    exports: [
        AlarmListComponent
    ]
})
export class AlarmsModule { }
