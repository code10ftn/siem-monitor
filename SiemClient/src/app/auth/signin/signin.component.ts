import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { AuthService } from '../../core/http/auth.service';
import { NotificationService } from '../../core/http/notification.service';
import { CAN_GET_LOG } from '../../core/util/constants';
import { User } from '../../shared/model/user.model';

@Component({
  selector: 'siem-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  user: User = new User();

  constructor(private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
  }

  signin() {
    this.authService.authenticate(this.user).subscribe(
      response => {
        this.toastr.success(`Welcome ${this.user.username}`);
        this.notificationService.connect();
        if (this.authService.checkPermission(CAN_GET_LOG)) {
          this.router.navigateByUrl('logs');
        }
      },
      err => { });
  }
}
