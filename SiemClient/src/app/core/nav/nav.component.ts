import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../http/auth.service';
import { CAN_GET_ALARM, CAN_GET_LOG, CAN_GET_REPORT, CAN_GET_RULE, CAN_POST_RULE } from '../util/constants';

@Component({
  selector: 'siem-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  isCollapsed = true;

  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  signout() {
    this.authService.signout();
    this.router.navigateByUrl('signin');
  }

  canReadLogs() {
    return this.authService.checkPermission(CAN_GET_LOG);
  }

  canPostRule() {
    return this.authService.checkPermission(CAN_POST_RULE);
  }

  canGetRules() {
    return this.authService.checkPermission(CAN_GET_RULE);
  }

  canGetAlarms() {
    return this.authService.checkPermission(CAN_GET_ALARM);
  }

  canGetReport() {
    return this.authService.checkPermission(CAN_GET_REPORT);
  }
}
