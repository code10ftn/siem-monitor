import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SigninComponent } from './signin/signin.component';

@NgModule({
    imports: [
        CommonModule,
        SharedModule
    ],
    declarations: [
        SigninComponent,
        ChangePasswordComponent
    ],
    exports: [
        SigninComponent,
        ChangePasswordComponent
    ]
})
export class AuthModule { }