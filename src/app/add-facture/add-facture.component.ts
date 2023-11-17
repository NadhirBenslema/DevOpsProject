import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-facture',
  templateUrl: './add-facture.component.html',
  styleUrls: ['./add-facture.component.css']
})
export class AddFactureComponent implements OnInit {

  facture: any = {
    montantFacture: '',
    montantRemise: '',
    dateCreationFacture: '',
   
  };
  fournisseurs: any[] = [];
  apiUrl = 'http://192.168.2.2:8089/SpringMVC/';

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {

  }

  navigateToListFacture(): void {
    this.router.navigate(['/listFacture']);
  }


addFacture(): void {
  console.log('Facture to be added:', this.facture); // Log before making the HTTP request

  this.http.post<any>(`${this.apiUrl}/facture/add-facture`, JSON.stringify(this.facture), {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .subscribe(
      (facture) => {
        console.log('Facture added successfully', facture);
        this.router.navigate(['/listFacture']);
      },
      (error) => {
        console.error('Error adding facture', error);
      }
    );
}


}
