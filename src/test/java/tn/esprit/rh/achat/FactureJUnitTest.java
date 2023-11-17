package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.controllers.FactureRestController;
import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.services.FactureServiceImpl;

import java.util.Date;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FactureJUnitTest {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureServiceImpl factureService;

    @Autowired
    FactureRestController factureController;
    @Test
    void testAddFacture() {
        
        Facture facture = new Facture();
        facture.setMontantRemise(120.0f);
        facture.setMontantFacture(720.0f);
        facture.setDateCreationFacture(new Date());
        facture.setDateDerniereModificationFacture(new Date());
        facture.setArchivee(false);

        
        Facture savedFacture = factureRepository.save(facture);

        
        assertNotNull(savedFacture.getIdFacture());
        assertEquals(facture.getMontantRemise(), savedFacture.getMontantRemise(), 0.001);
        assertEquals(facture.getMontantFacture(), savedFacture.getMontantFacture(), 0.001);
        assertEquals(facture.getDateCreationFacture(), savedFacture.getDateCreationFacture());
        assertEquals(facture.getDateDerniereModificationFacture(), savedFacture.getDateDerniereModificationFacture());
        assertEquals(facture.getArchivee(), savedFacture.getArchivee());

        
        Facture retrievedFacture = factureRepository.findById(savedFacture.getIdFacture()).orElse(null);
        System.out.println("Retrived:"+ retrievedFacture.getIdFacture());
        System.out.println("Saved:"+ savedFacture.getIdFacture());
        assertNotNull(retrievedFacture);
        assertEquals(savedFacture.getIdFacture(), retrievedFacture.getIdFacture());
    }


    @Test
    void testCancelFacture() {
        
        Facture factureToCancel = new Facture();
        factureToCancel.setMontantRemise(120.0f);
        factureToCancel.setMontantFacture(720.0f);
        factureToCancel.setDateCreationFacture(new Date());
        factureToCancel.setDateDerniereModificationFacture(new Date());
        factureToCancel.setArchivee(false);

        
        Facture savedFacture = factureRepository.save(factureToCancel);
        Facture dataBaseCheck= factureRepository.findById(savedFacture.getIdFacture()).orElse(null);
        System.out.println("Saved Facture in database: " + dataBaseCheck);

        
        factureService.cancelFacture(savedFacture.getIdFacture());

        
        Facture canceledFacture = factureRepository.findById(savedFacture.getIdFacture()).orElse(null);
        System.out.println("Canceled Facture from DB: " + canceledFacture);

        
        assertNotNull(canceledFacture);
        assertTrue(canceledFacture.getArchivee());
    }

    @Test
    void testDeleteFacture() {
        
        Facture factureToDelete = new Facture();
        factureToDelete.setMontantRemise(120.0f);
        factureToDelete.setMontantFacture(720.0f);
        factureToDelete.setDateCreationFacture(new Date());
        factureToDelete.setDateDerniereModificationFacture(new Date());
        factureToDelete.setArchivee(false);

        
        Facture savedFacture = factureRepository.save(factureToDelete);

        
        Facture databaseCheck = factureRepository.findById(savedFacture.getIdFacture()).orElse(null);
        assertNotNull(databaseCheck);
        System.out.println("Saved Facture in database: " + databaseCheck);

        
        String response = factureController.deleteFacture(savedFacture.getIdFacture());

        
        assertEquals("Facture deleted successfully", response);

        
        Facture deletedFacture = factureRepository.findById(savedFacture.getIdFacture()).orElse(null);
        assertNull(deletedFacture);
    }

}
