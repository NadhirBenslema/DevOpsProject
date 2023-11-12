package tn.esprit.rh.achat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.rh.achat.entities.Fournisseur;

import java.util.List;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    List<Fournisseur> findFournisseursByLibelle(String libelle);
    List<Fournisseur> findFournisseursByCode(String code);
    List<Fournisseur> findFournisseursByCategorieFournisseur(String category);

    Fournisseur findFournisseurByDetailFournisseurIdDetailFournisseur(Long id);


}
