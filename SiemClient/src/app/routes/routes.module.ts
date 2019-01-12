import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AlarmListComponent } from '../alarms/alarm-list/alarm-list.component';
import { ChangePasswordComponent } from '../auth/change-password/change-password.component';
import { SigninComponent } from '../auth/signin/signin.component';
import { LogsListComponent } from '../logs/logs-list/logs-list.component';
import { GetReportComponent } from '../reports/get-report/get-report.component';
import { AddRuleComponent } from '../rules/add-rule/add-rule.component';
import { EditRuleComponent } from '../rules/edit-rule/edit-rule.component';
import { RulesListComponent } from '../rules/rules-list/rules-list.component';
import { CanAddRuleGuard } from './can-add-rule.guard';
import { CanEditRuleGuard } from './can-edit-rule.guard';
import { CanGetAlarmsGuard } from './can-get-alarms.guard';
import { CanGetReportGuard } from './can-get-report.guard';
import { CanGetRuleGuard } from './can-get-rule.guard';
import { CanReadLogsGuard } from './can-read-logs.guard';
import { IsAuthenticatedGuard } from './is-authenticated.guard';
import { IsUnuthenticatedGuard } from './is-unauthenticated.guard';

const routes: Routes = [
  { path: '', redirectTo: '/logs', pathMatch: 'full' },

  // Auth
  { path: 'signin', component: SigninComponent, canActivate: [IsUnuthenticatedGuard] },

  // Logs
  { path: 'logs', component: LogsListComponent, canActivate: [CanReadLogsGuard] },

  // Rules
  { path: 'rules-list', component: RulesListComponent, canActivate: [CanGetRuleGuard] },
  { path: 'add-rule', component: AddRuleComponent, canActivate: [CanAddRuleGuard] },
  { path: 'rule-details/:id', component: EditRuleComponent, canActivate: [CanEditRuleGuard] },

  // Alarms
  { path: 'alarms-list', component: AlarmListComponent, canActivate: [CanGetAlarmsGuard] },

  // Report
  { path: 'report', component: GetReportComponent, canActivate: [CanGetReportGuard] },

  // Change Password
  { path: 'changePassword', component: ChangePasswordComponent, canActivate: [IsAuthenticatedGuard] }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  providers: [
    IsAuthenticatedGuard,
    IsUnuthenticatedGuard,
    CanAddRuleGuard,
    CanEditRuleGuard,
    CanGetAlarmsGuard,
    CanGetReportGuard,
    CanGetRuleGuard,
    CanReadLogsGuard]
})
export class RoutesModule { }
