package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.*;
import tn.esprit.rh.achat.repositories.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class FactureServiceImpl implements IFactureService {

	@Autowired
	FactureRepository factureRepository;
	@Autowired
	OperateurRepository operateurRepository;
	@Autowired
	DetailFactureRepository detailFactureRepository;
	@Autowired
	FournisseurRepository fournisseurRepository;
	@Autowired
	ProduitRepository produitRepository;
	@Autowired
	ReglementServiceImpl reglementServiceIml;

	@Override
	public List<Facture> retrieveAllFactures() {
		log.info("Entering retrieveAllFactures method.");
		List<Facture> factures = (List<Facture>) factureRepository.findAll();
		for (Facture facture : factures) {
			log.info(" facture : " + facture);
		}
		return factures;
	}


	public Facture addFacture(Facture f) {
		log.info("Entering addFacture method with parameter: {}", f);
		return factureRepository.save(f);
	}

	/*
	 * calculer les montants remise et le montant total d'un détail facture
	 * ainsi que les montants d'une facture
	 */
	private Facture addDetailsFacture(Facture f, Set<DetailFacture> detailsFacture) {
		log.info("Entering addDetailsFacture method with parameters: {} and {}", f, detailsFacture);
		float montantFacture = 0;
		float montantRemise = 0;
		for (DetailFacture detail : detailsFacture) {
			//Récuperer le produit
			Produit produit = produitRepository.findById(detail.getProduit().getIdProduit()).get();
			//Calculer le montant total pour chaque détail Facture
			float prixTotalDetail = detail.getQteCommandee() * produit.getPrix();
			//Calculer le montant remise pour chaque détail Facture
			float montantRemiseDetail = (prixTotalDetail * detail.getPourcentageRemise()) / 100;
			float prixTotalDetailRemise = prixTotalDetail - montantRemiseDetail;
			detail.setMontantRemise(montantRemiseDetail);
			detail.setPrixTotalDetail(prixTotalDetailRemise);
			//Calculer le montant total pour la facture
			montantFacture = montantFacture + prixTotalDetailRemise;
			//Calculer le montant remise pour la facture
			montantRemise = montantRemise + montantRemiseDetail;
			detailFactureRepository.save(detail);
		}
		f.setMontantFacture(montantFacture);
		f.setMontantRemise(montantRemise);
		log.info("Details added to facture: {}", f);
		return f;
	}

	@Override
	public void cancelFacture(Long factureId) {
		log.info("Entering cancelFacture method with parameter: {}", factureId);
		// Méthode 01
		//Facture facture = factureRepository.findById(factureId).get();
		Facture facture = factureRepository.findById(factureId).orElse(new Facture());
		facture.setArchivee(true);
		factureRepository.save(facture);
		log.info("Facture canceled: {}", facture);
		//Méthode 02 (Avec JPQL)
		factureRepository.updateFacture(factureId);
	}

	@Override
	public Facture retrieveFacture(Long factureId) {
		log.info("Entering retrieveFacture method with parameter: {}", factureId);
		Facture facture = factureRepository.findById(factureId).orElse(null);
		log.info("facture :" + facture);
		return facture;
	}

	@Override
	public List<Facture> getFacturesByFournisseur(Long idFournisseur) {
		log.info("Entering getFacturesByFournisseur method with parameter: {}", idFournisseur);
		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur).orElse(null);
		List<Facture> factures = (List<Facture>) fournisseur.getFactures();
		log.info("Retrieving factures for fournisseur {}: {}", idFournisseur, factures);
		return factures;
	}

	@Override
	public void assignOperateurToFacture(Long idOperateur, Long idFacture) {
		log.info("Entering assignOperateurToFacture method with parameters: {} and {}", idOperateur, idFacture);
		Facture facture = factureRepository.findById(idFacture).orElse(null);
		Operateur operateur = operateurRepository.findById(idOperateur).orElse(null);
		operateur.getFactures().add(facture);
		operateurRepository.save(operateur);
		log.info("Assigned operateur {} to facture {}", idOperateur, idFacture);
	}

	@Override
	public float pourcentageRecouvrement(Date startDate, Date endDate) {
		log.info("Entering pourcentageRecouvrement method with parameters: {} and {}", startDate, endDate);
		float totalFacturesEntreDeuxDates = factureRepository.getTotalFacturesEntreDeuxDates(startDate,endDate);
		float totalRecouvrementEntreDeuxDates =reglementServiceIml.getChiffreAffaireEntreDeuxDate(startDate,endDate);
		float pourcentage=(totalRecouvrementEntreDeuxDates/totalFacturesEntreDeuxDates)*100;
		log.info("Pourcentage recouvrement calculated: {}%", pourcentage);
		return pourcentage;
	}
	@Override
	public Set<DetailFacture> getDetailsForFacture(Long factureId) {
		log.info("Getting details for facture with ID: {}", factureId);

		// Retrieve the facture from the repository
		Facture facture = factureRepository.findById(factureId)
				.orElse(null);

		// Retrieve details associated with the facture
		Set<DetailFacture> detailsFacture = facture.getDetailsFacture();

		log.info("Details retrieved for facture with ID {}: {}", factureId, detailsFacture);
		return detailsFacture;
	}

	public void deleteFacture(Long factureId) {
		log.info("Entering deleteFacture method with parameter: {}", factureId);

		// Check if facture exists
		Facture existingFacture = factureRepository.findById(factureId).orElse(null);
		if (existingFacture != null) {
			factureRepository.delete(existingFacture);
			log.info("Facture with ID {} deleted successfully.", factureId);
		} else {
			log.warn("Facture with ID {} not found. Unable to delete.", factureId);
		}
	}
}
