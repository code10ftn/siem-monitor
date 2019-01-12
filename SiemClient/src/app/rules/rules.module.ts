import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { SharedModule } from '../shared/shared.module';
import { AddRuleComponent } from './add-rule/add-rule.component';
import { EditRuleComponent } from './edit-rule/edit-rule.component';
import { RuleFormComponent } from './rule-form/rule-form.component';
import { RulesListComponent } from './rules-list/rules-list.component';

@NgModule({
    imports: [
        CommonModule,
        ButtonsModule.forRoot(),
        NgxDatatableModule,
        SharedModule
    ],
    declarations: [
        AddRuleComponent,
        EditRuleComponent,
        RuleFormComponent,
        RulesListComponent
    ],
    exports: [
        AddRuleComponent,
        EditRuleComponent,
        RuleFormComponent,
        RulesListComponent
    ]
})
export class RulesModule { }