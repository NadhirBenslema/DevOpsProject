package tn.esprit.rh.achat.services;

import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;

import java.util.List;

public interface IFournisseurService {

	List<Fournisseur> retrieveAllFournisseurs();

	Fournisseur addFournisseur(Fournisseur f);

	void deleteFournisseur(Long id);

	Fournisseur updateFournisseur(Fournisseur f);

	Fournisseur retrieveFournisseur(Long id);

	List<Fournisseur> retrieveFournisseurByCode(String code);
	List<Fournisseur> retrieveFournisseurByLibelle(String libelle);

	List<Fournisseur> retrieveFournisseurByCategory(String category);

	Fournisseur retrieveFournisseurByDetail(Long id);



	List<DetailFournisseur> retrieveAllDetailsFournisseurs();

	void deleteDetailFournisseur(Long id);

	DetailFournisseur updateDetailFournisseur(DetailFournisseur df , Long id);
	DetailFournisseur retrieveDetailFournisseur(Long id);



	void assignSecteurActiviteToFournisseur(Long idSecteurActivite, Long idFournisseur);

}
