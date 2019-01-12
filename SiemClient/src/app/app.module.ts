import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { AlarmsModule } from './alarms/alarms.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { CoreModule } from './core/core.module';
import { LogsModule } from './logs/logs.module';
import { ReportsModule } from './reports/reports.module';
import { RulesModule } from './rules/rules.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AlarmsModule,
    AuthModule,
    BrowserModule,
    CoreModule,
    LogsModule,
    NgxDatatableModule,
    ReportsModule,
    RulesModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
