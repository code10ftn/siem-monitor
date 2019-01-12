import { Component } from '@angular/core';

import { AuthService } from './core/http/auth.service';
import { NotificationService } from './core/http/notification.service';

@Component({
  selector: 'siem-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(private authService: AuthService,
    private notificationService: NotificationService) { }

  ngOnInit() {
    if (this.authService.isAuthenticated()) {
      this.notificationService.connect();
    }
  }
}
