import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable()
export class NotificationService {

  private serverUrl = 'https://localhost:8080/api/notifications';

  private stompClient;

  constructor(private toastr: ToastrService) {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    let wss = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(wss);
  }

  connect() {
    let wss = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(wss);

    let that = this;

    this.stompClient.connect({}, function (frame) {
      that.stompClient.subscribe("/notifications", (message) => {
        if (message.body) {
          that.toastr.warning(message.body);
        }
      });
    });
  }

  disconnect() {
    this.stompClient.disconnect();
  }
}
