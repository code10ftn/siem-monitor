import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { AuthService } from '../../core/http/auth.service';
import { UserService } from '../../core/http/user.service';
import { ChangePasswordDto } from '../../shared/model/change-password-dto';

@Component({
  selector: 'siem-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  dto = new ChangePasswordDto();

  constructor(private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
  }

  changePassword() {
    this.userService.changePassword(this.authService.getAuthenticatedUserId(), this.dto).subscribe(
      response => {
        this.toastr.success('Password successfully changed!');
      },
      err => { });
  }
}
