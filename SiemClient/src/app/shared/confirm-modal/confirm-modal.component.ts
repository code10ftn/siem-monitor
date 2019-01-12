import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs/Subject';

@Component({
  selector: 'siem-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.css']
})
export class ConfirmModalComponent implements OnInit {

  onClose: Subject<boolean>;

  constructor(private modalRef: BsModalRef) { }

  ngOnInit() {
    this.onClose = new Subject();
  }

  onConfirm() {
    this.onClose.next(true);
    this.modalRef.hide();
  }

  onCancel() {
    this.onClose.next(false);
    this.modalRef.hide();
  }
}
