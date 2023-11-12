package tn.esprit.rh.achat.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.services.IFournisseurService;

import java.util.List;


@RestController
@Api(tags = "Gestion des fournisseurss")
@RequestMapping("/fournisseur")
@CrossOrigin("*")
public class FournisseurRestController {

	@Autowired
	IFournisseurService fournisseurService;

	// http://localhost:8089/SpringMVC/fournisseur/retrieve-all-fournisseurs
	@GetMapping("/retrieve-all-fournisseurs")
	@ResponseBody
	public List<Fournisseur> getFournisseurs() {
		return fournisseurService.retrieveAllFournisseurs();
	}

	// http://localhost:8089/SpringMVC/fournisseur/retrieve-fournisseur/8
	@GetMapping("/retrieve-fournisseur/{fournisseur-id}")
	@ResponseBody
	public Fournisseur retrieveFournisseur(@PathVariable("fournisseur-id") Long fournisseurId) {
		return fournisseurService.retrieveFournisseur(fournisseurId);
	}

	// http://localhost:8089/SpringMVC/fournisseur/add-fournisseur
	@PostMapping("/add-fournisseur")
	@ResponseBody
	public Fournisseur addFournisseur(@RequestBody Fournisseur f) {
		return fournisseurService.addFournisseur(f);
	}


	@DeleteMapping("/remove-fournisseur/{fournisseur-id}")
	@ResponseBody
	public void removeFournisseur(@PathVariable("fournisseur-id") Long fournisseurId) {
		fournisseurService.deleteFournisseur(fournisseurId);
	}

	// http://localhost:8089/SpringMVC/fournisseur/modify-fournisseur
	@PutMapping("/modify-fournisseur")
	@ResponseBody
	public Fournisseur modifyFournisseur(@RequestBody Fournisseur fournisseur) {
		return fournisseurService.updateFournisseur(fournisseur);
	}


	// http://localhost:8089/SpringMVC/fournisseur/retrieve-code/code
	@GetMapping("/retrieve-code/{code}")
	@ResponseBody
	public List<Fournisseur> retrieveFournisseurCode(@PathVariable("code") String code) {
		return fournisseurService.retrieveFournisseurByCode(code);
	}

	// http://localhost:8089/SpringMVC/fournisseur/retrieve-libelle/libelle
	@GetMapping("/retrieve-libelle/{libelle}")
	@ResponseBody
	public List<Fournisseur> retrieveFournisseurLibelle(@PathVariable("libelle") String libelle) {
		return fournisseurService.retrieveFournisseurByLibelle(libelle);
	}

	// http://localhost:8089/SpringMVC/fournisseur/retrieve-category/category
	@GetMapping("/retrieve-category/{category}")
	@ResponseBody
	public List<Fournisseur> retrieveFournisseurCategory(@PathVariable("category") String category) {
		return fournisseurService.retrieveFournisseurByCategory(category);
	}

	// http://localhost:8089/SpringMVC/fournisseur/retrieve-detail/2
	@GetMapping("/retrieve-detail/{id}")
	@ResponseBody
	public Fournisseur retrieveFournisseurByDetail(@PathVariable("id") Long id) {
		return fournisseurService.retrieveFournisseurByDetail(id);
	}




	// http://localhost:8089/SpringMVC/fournisseur/retrieve-all-details
	@GetMapping("/retrieve-all-details")
	@ResponseBody
	public List<DetailFournisseur> getDetailsFournisseurs() {
		return fournisseurService.retrieveAllDetailsFournisseurs();
	}

	// http://localhost:8089/SpringMVC/fournisseur/retrieve-detailF/8
	@GetMapping("/retrieve-detailF/{detail-id}")
	@ResponseBody
	public DetailFournisseur retrieveDetailFournisseur(@PathVariable("detail-id") Long detailId) {
		return fournisseurService.retrieveDetailFournisseur(detailId);
	}



	// http://localhost:8089/SpringMVC/fournisseur/assignSecteurActiviteToFournisseur/1/5
		@PutMapping(value = "/assignSecteurActiviteToFournisseur/{idSecteurActivite}/{idFournisseur}")
		public void assignProduitToStock(@PathVariable("idSecteurActivite") Long idSecteurActivite, @PathVariable("idFournisseur") Long idFournisseur) {
			fournisseurService.assignSecteurActiviteToFournisseur(idSecteurActivite, idFournisseur);
		}

}
