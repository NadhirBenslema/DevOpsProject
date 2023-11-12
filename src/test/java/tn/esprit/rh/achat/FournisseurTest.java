package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;

import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.*;

import tn.esprit.rh.achat.services.FournisseurServiceImpl;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
@ContextConfiguration(classes = {FournisseurServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class FournisseurTest {




    @MockBean
    private FournisseurRepository fournisseurRepository;

    @MockBean
    private DetailFournisseurRepository detailFournisseurRepository;

    @MockBean
    private ProduitRepository produitRepository;

    @MockBean
    private SecteurActiviteRepository secteurActiviteRepository;

    @MockBean
    private FactureRepository factureRepository;

    @MockBean
    private DetailFactureRepository detailFactureRepository;

    @MockBean
    private OperateurRepository operateurRepository;

    @Autowired
    private FournisseurServiceImpl fournisseurService;



    @Test
     void testRetrieveAllFournisseurs() {

        ArrayList<Fournisseur> fournisseurList = new ArrayList<>();
        when(fournisseurRepository.findAll()).thenReturn(fournisseurList);
        List<Fournisseur> actualRetrieveAllFournisseursResult = fournisseurService.retrieveAllFournisseurs();
        assertSame(fournisseurList, actualRetrieveAllFournisseursResult);
        assertTrue(actualRetrieveAllFournisseursResult.isEmpty());
        verify(fournisseurRepository).findAll();
    }


    @Test
    void testAddFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);

        when(detailFournisseurRepository.save(any(DetailFournisseur.class))).thenReturn(detailFournisseur);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        Fournisseur result = fournisseurService.addFournisseur(fournisseur);

        assertNotNull(result);
    }




    @Test
    void testUpdateFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);

        when(detailFournisseurRepository.save(any(DetailFournisseur.class))).thenReturn(detailFournisseur);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        Fournisseur result = fournisseurService.updateFournisseur(fournisseur);

        assertNotNull(result);
        assertEquals(detailFournisseur, result.getDetailFournisseur());
    }


    @Test
    void testDeleteFournisseur() {
        doNothing().when(fournisseurRepository).deleteById((Long) any());
        fournisseurService.deleteFournisseur(123L);
        verify(fournisseurRepository).deleteById((Long) any());
    }


    @Test
    void testRetrieveFournisseur() {
        Long fournisseurId = 1L;
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setIdFournisseur(fournisseurId);
        // Configurer le comportement du mock FournisseurRepository
        when(fournisseurRepository.findById(fournisseurId)).thenReturn(Optional.of(fournisseur));
        Fournisseur result = fournisseurService.retrieveFournisseur(fournisseurId);
        assertNotNull(result);
        assertEquals(fournisseur, result);
        verify(fournisseurRepository).findById(fournisseurId);
    }



    @Test
    void testSaveDetailFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);

        when(detailFournisseurRepository.save(detailFournisseur)).thenReturn(detailFournisseur);
        DetailFournisseur result = fournisseurService.saveDetailFournisseur(fournisseur);
        assertNotNull(result);
        assertEquals(detailFournisseur, result);

        verify(detailFournisseurRepository).save(detailFournisseur);
    }



    @Test
    void testRetrieveFournisseurByCode() {
        String code = "exampleCode";
        List<Fournisseur> fournisseurs = new ArrayList<>();
        when(fournisseurRepository.findFournisseursByCode(code)).thenReturn(fournisseurs);

        List<Fournisseur> result = fournisseurService.retrieveFournisseurByCode(code);
        assertEquals(fournisseurs, result);
    }

    // Test retrieveFournisseurByLibelle
    @Test
    void testRetrieveFournisseurByLibelle() {
        String libelle = "exampleLibelle";
        List<Fournisseur> fournisseurs = new ArrayList<>();
        when(fournisseurRepository.findFournisseursByLibelle(libelle)).thenReturn(fournisseurs);
        List<Fournisseur> result = fournisseurService.retrieveFournisseurByLibelle(libelle);
        assertEquals(fournisseurs, result);
    }


    @Test
    void testRetrieveFournisseurByCategory() {
        String category = "exampleCategory";
        List<Fournisseur> fournisseurs = new ArrayList<>();
        when(fournisseurRepository.findFournisseursByCategorieFournisseur(category)).thenReturn(fournisseurs);
        List<Fournisseur> result = fournisseurService.retrieveFournisseurByCategory(category);
        assertEquals(fournisseurs, result);
    }

    @Test
    void testRetrieveFournisseurByDetail() {
        Long id = 1L;
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.findFournisseurByDetailFournisseurIdDetailFournisseur(id)).thenReturn(fournisseur);
        Fournisseur result = fournisseurService.retrieveFournisseurByDetail(id);
        assertEquals(fournisseur, result);
    }



    // Test retrieveAllDetailsFournisseurs
    @Test
    void testRetrieveAllDetailsFournisseurs() {
        List<DetailFournisseur> expectedDetailFournisseurs = new ArrayList<>();
        // Ajoutez des détails fournisseurs fictifs à la liste expectedDetailFournisseurs

        when(detailFournisseurRepository.findAll()).thenReturn(expectedDetailFournisseurs);

        List<DetailFournisseur> result = fournisseurService.retrieveAllDetailsFournisseurs();
        assertEquals(expectedDetailFournisseurs, result);
    }

    // Test deleteDetailFournisseur
    @Test
    void testDeleteDetailFournisseur() {
        Long id = 1L;

        fournisseurService.deleteDetailFournisseur(id);
        verify(detailFournisseurRepository).deleteById(id);
    }

    // Test updateDetailFournisseur
    @Test
    void testUpdateDetailFournisseur() {
        Long id = 1L;
        DetailFournisseur existingDetail = new DetailFournisseur(); // Créez un objet existant simulé
        DetailFournisseur updatedDetail = new DetailFournisseur(); // Créez un objet mis à jour simulé

        when(detailFournisseurRepository.findById(id)).thenReturn(Optional.of(existingDetail));
        when(detailFournisseurRepository.save(existingDetail)).thenReturn(updatedDetail);

        DetailFournisseur result = fournisseurService.updateDetailFournisseur(updatedDetail, id);
        assertEquals(updatedDetail, result);
    }

    // Test retrieveDetailFournisseur
    @Test
    void testRetrieveDetailFournisseur() {
        Long id = 1L;
        DetailFournisseur expectedDetail = new DetailFournisseur(); // Créez un détail fictif attendu

        when(detailFournisseurRepository.findById(id)).thenReturn(Optional.of(expectedDetail));

        DetailFournisseur result = fournisseurService.retrieveDetailFournisseur(id);
        assertEquals(expectedDetail, result);
    }


  /*  @Test
    void testAssignSecteurActiviteToFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        SecteurActivite secteurActivite = new SecteurActivite();
        Long idSecteurActivite = 1L;
        Long idFournisseur = 2L;
        when(fournisseurRepository.findById(any(Long.class))).thenReturn(Optional.of(fournisseur));
        when(secteurActiviteRepository.findById(any(Long.class))).thenReturn(Optional.of(secteurActivite));
        fournisseurService.assignSecteurActiviteToFournisseur(idSecteurActivite, idFournisseur);
        assertTrue(fournisseur.getSecteurActivites().contains(secteurActivite));
        verify(fournisseurRepository).save(fournisseur);
    } */







}
