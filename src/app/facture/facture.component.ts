import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-facture',
  templateUrl: './facture.component.html',
  styleUrls: ['./facture.component.css']
})
export class FactureComponent implements OnInit {

  facture: any[] = [];
  apiUrl = 'http://localhost:8089/SpringMVC/facture'; 
  factureId: string | null = null;
  constructor(private http: HttpClient,private route: ActivatedRoute,private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const factureId = params.get('id');
      this.getFactureDetails(factureId);
    });
  }

  getFactureDetails(factureId: string | null): void {
    if (factureId) {
      this.http.get<any>(`http://localhost:8089/SpringMVC/facture/retrieve-facture/${factureId}`)
        .subscribe(
          facture => {
            this.facture = [facture]; // Wrap the single object in an array
            console.log("Data:", this.facture); // Move the console.log here
          },
          error => console.error('Error fetching facture details', error)
        );
    }
}
navigateToListFacture(): void {
  this.router.navigate(['/listFacture']);
}
}
