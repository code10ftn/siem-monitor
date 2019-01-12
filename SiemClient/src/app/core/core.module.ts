import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { RoutesModule } from '../routes/routes.module';
import { SharedModule } from '../shared/shared.module';
import { AlarmService } from './http/alarm.service';
import { AuthService } from './http/auth.service';
import { LogService } from './http/log.service';
import { NotificationService } from './http/notification.service';
import { ReportService } from './http/report.service';
import { RuleService } from './http/rule.service';
import { TokenInterceptorService } from './http/token-interceptor.service';
import { UserService } from './http/user.service';
import { NavComponent } from './nav/nav.component';
import { TokenUtilsService } from './util/token-utils.service';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    RoutesModule,
    SharedModule,
    ToastrModule.forRoot({
      preventDuplicates: true,
      positionClass: 'toast-position'
    })
  ],
  declarations: [NavComponent],
  exports: [
    NavComponent,
    RoutesModule
  ],
  providers: [
    AlarmService,
    AuthService,
    LogService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    NotificationService,
    ReportService,
    RuleService,
    TokenUtilsService,
    UserService]
})
export class CoreModule { }
