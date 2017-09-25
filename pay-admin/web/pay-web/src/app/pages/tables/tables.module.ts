import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MdTableModule, MdPaginatorModule } from '@angular/material';
import { CdkTableModule } from '@angular/cdk';

import { TablesComponent } from './tables.component';
import { SmartTablesComponent } from './component/smart-tables/smart-tables.component';

import { TablesRouting } from './tables.routing';

@NgModule({
  imports: [
    CommonModule,
    MdTableModule,
    MdPaginatorModule,
    CdkTableModule,
    TablesRouting,
  ],
  declarations: [TablesComponent, SmartTablesComponent]
})
export class TablesModule { }
