import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FactureComponent } from './facture/facture.component';
import { ListFactureComponent } from './list-facture/list-facture.component';
import { AddFactureComponent } from './add-facture/add-facture.component';

const routes: Routes = [
    {path:'facture/:id',component: FactureComponent},
    {path:'listFacture',component: ListFactureComponent},
    {path:'addFacture',component: AddFactureComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
