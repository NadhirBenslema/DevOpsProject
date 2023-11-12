package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FournisseurServiceImpl implements IFournisseurService {

	@Autowired
	FournisseurRepository fournisseurRepository;
	@Autowired
	DetailFournisseurRepository detailFournisseurRepository;
	@Autowired
	ProduitRepository produitRepository;
	@Autowired
	SecteurActiviteRepository secteurActiviteRepository;

	@Override
	public List<Fournisseur> retrieveAllFournisseurs() {
		List<Fournisseur> fournisseurs = (List<Fournisseur>) fournisseurRepository.findAll();
		for (Fournisseur fournisseur : fournisseurs) {
			log.info(" fournisseur : " + fournisseur);
		}
		return fournisseurs;
	}


	public Fournisseur addFournisseur(Fournisseur f /*Master*/) {
		DetailFournisseur df= new DetailFournisseur();//Slave
		df.setDateDebutCollaboration(new Date()); //util
		//On affecte le "Slave" au "Master"
		f.setDetailFournisseur(df);	
		fournisseurRepository.save(f);
		return f;
	}
	
	public DetailFournisseur  saveDetailFournisseur(Fournisseur f){
		DetailFournisseur df = f.getDetailFournisseur();
		detailFournisseurRepository.save(df);
		return df;
	}

	public Fournisseur updateFournisseur(Fournisseur f) {
		DetailFournisseur df = saveDetailFournisseur(f);
		f.setDetailFournisseur(df);	
		fournisseurRepository.save(f);
		return f;
	}

	@Override
	public void deleteFournisseur(Long fournisseurId) {
		fournisseurRepository.deleteById(fournisseurId);

	}

	@Override
	public Fournisseur retrieveFournisseur(Long fournisseurId) {

		return  fournisseurRepository.findById(fournisseurId).orElse(null);

	}

	@Override
	public List<Fournisseur> retrieveFournisseurByCode(String code) {
		return fournisseurRepository.findFournisseursByCode(code);
	}

	@Override
	public List<Fournisseur> retrieveFournisseurByLibelle(String libelle) {
		return fournisseurRepository.findFournisseursByLibelle(libelle);
	}

	@Override
	public List<Fournisseur> retrieveFournisseurByCategory(String category) {
		return fournisseurRepository.findFournisseursByCategorieFournisseur(category);
	}

	@Override
	public Fournisseur retrieveFournisseurByDetail(Long id) {
		return fournisseurRepository.findFournisseurByDetailFournisseurIdDetailFournisseur(id);
	}

	@Override
	public List<DetailFournisseur> retrieveAllDetailsFournisseurs() {
		List<DetailFournisseur> detailFournisseurs = (List<DetailFournisseur>) detailFournisseurRepository.findAll();
		for (DetailFournisseur detailFournisseur : detailFournisseurs) {
			log.info(" detail fournisseurs : " + detailFournisseur);
		}
		return detailFournisseurs;
	}

	@Override
	public void deleteDetailFournisseur(Long id) {
		detailFournisseurRepository.deleteById(id);

	}

	@Override
	public DetailFournisseur updateDetailFournisseur(DetailFournisseur df,Long id) {
		if (detailFournisseurRepository.findById(id).isPresent()) {
			DetailFournisseur existingDetail = detailFournisseurRepository.findById(id).get();
			existingDetail.setEmail(df.getEmail());
			existingDetail.setDateDebutCollaboration(df.getDateDebutCollaboration());
			existingDetail.setAdresse(df.getAdresse());
			existingDetail.setMatricule(df.getMatricule());

			return detailFournisseurRepository.save(existingDetail);
		}
		return null;
	}
	@Override
	public DetailFournisseur retrieveDetailFournisseur(Long id) {
		return  detailFournisseurRepository.findById(id).orElse(null);
	}




	@Override
	public void assignSecteurActiviteToFournisseur(Long idSecteurActivite, Long idFournisseur) {
		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur).orElse(null);
		SecteurActivite secteurActivite = secteurActiviteRepository.findById(idSecteurActivite).orElse(null);
        fournisseur.getSecteurActivites().add(secteurActivite);
        fournisseurRepository.save(fournisseur);
		
		
	}






}