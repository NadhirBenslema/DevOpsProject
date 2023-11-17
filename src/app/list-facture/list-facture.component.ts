import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-facture',
  templateUrl: './list-facture.component.html',
  styleUrls: ['./list-facture.component.css']
})
export class ListFactureComponent implements OnInit {
  factures: any[] | undefined; // You may need to create a TypeScript interface to represent the Facture model
   apiUrl = 'http://192.168.2.2:8089/SpringMVC/facture'; 
   apiUrlF = 'http://192.168.2.2:8089/SpringMVC'; 
   showModal = false;
   fournisseursList: any[] = [];
   isEditing: boolean = false;

editingFactureId: number | null = null;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.getFactures();
  }

  getFactures(): void {
    this.http.get<any[]>(`${this.apiUrl}/retrieve-all-factures`)
      .subscribe(
        factures => this.factures = factures,
        error => console.error('Error fetching factures', error)
      );
  }

  redirectToFactureDetails(facture: any): void {
    if (facture && facture.idFacture) {
      this.router.navigate(['/facture', facture.idFacture]);
    }
  }

  navigateToAddFacture(): void {
    this.router.navigate(['/addFacture']); // Replace with the actual route for adding facture
}

toggleCancelFacture(facture: any): void {
  if (!facture.archivee && facture.idFacture) {
    this.http.put<void>(`${this.apiUrl}/cancel-facture/${facture.idFacture}`, {})
      .subscribe(
        () => {
          console.log('Facture cancelled successfully.');
          facture.archivee = !facture.archivee; // Toggle the value in the UI
        },
        error => console.error('Error cancelling facture', error)
      );
  }
}

deleteFacture(factureId: number): void {
  if (confirm('Are you sure you want to delete this facture?')) {
    this.http.delete(`${this.apiUrl}/${factureId}`)
      .subscribe(
        () => {
          this.getFactures();
        },
        error => {
          console.error('Error deleting facture', error);
          this.getFactures(); // Refresh the list even if there's an error
        }
      );
  }
}

assignFournisseur(facture: any): void {
  // Assuming 'id' is the property in fournisseur representing its unique identifier
  const idFournisseur = facture.fournisseur.idFournisseur;
  const idFacture = facture.idFacture; // Replace this with the actual property representing the facture id

  this.assignFournisseurToFacture(idFournisseur, idFacture);
  this.isEditing = false; // Stop editing after assigning fournisseur
}

assignFournisseurToFacture(idFournisseur: number, idFacture: number): void {
  this.http.put<void>(`${this.apiUrl}/assignFournisseurToFacture/${idFournisseur}/${idFacture}`, {})
    .subscribe(
      () => {
        console.log(`Assigned fournisseur ${idFournisseur} to facture ${idFacture} successfully.`);
        this.getFactures();
      },
      error => {console.error('Error assigning fournisseur to facture', error);
      this.getFactures();}
    );

}

getFournisseurs(): void {
  this.http.get<any[]>(`${this.apiUrlF}/fournisseur/retrieve-all-fournisseurs`)
      .subscribe(
          fournisseurs => {
              this.fournisseursList = fournisseurs;
              console.log("Fournisseur", this.fournisseursList);
          },
          error => console.error('Error fetching fournisseurs', error)
      );
}

startEditing(facture: any): void {
 this.getFournisseurs()
  this.isEditing = true;
  this.editingFactureId = facture.idFacture;
  this.getFactures()
}

stopEditing(): void {
  this.isEditing = false;
  this.editingFactureId = null;
  this.getFactures()
}

}
