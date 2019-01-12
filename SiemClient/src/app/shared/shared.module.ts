import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';

import { ConfirmModalComponent } from './confirm-modal/confirm-modal.component';

@NgModule({
  imports: [
    AngularFontAwesomeModule,
    BsDropdownModule.forRoot(),
    CollapseModule.forRoot(),
    CommonModule,
    FormsModule,
    ModalModule.forRoot()
  ],
  declarations: [ConfirmModalComponent],
  exports: [
    BsDropdownModule,
    CollapseModule,
    FormsModule
  ],
  entryComponents: [ConfirmModalComponent]
})
export class SharedModule { }
