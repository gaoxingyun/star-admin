import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TablesComponent } from './tables.component';

import { SmartTablesComponent } from './component/smart-tables/smart-tables.component';

export const routes: Routes = [
  {
    path: '',
    component: TablesComponent,
    children: [
      { path: 'smarttables', component: SmartTablesComponent },
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class TablesRouting {}