package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.controllers.FactureRestController;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.services.IFactureService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ContextConfiguration(classes = {FactureRestController.class})
@ExtendWith(SpringExtension.class)
public class FactureTest {

    @MockBean
    private IFactureService factureService;

    @Autowired
    private FactureRestController factureRestController;

    @Test
    void testRetrieveAllFactures() {
        List<Facture> factureList = new ArrayList<>();
        when(factureService.retrieveAllFactures()).thenReturn(factureList);
        List<Facture> actualFactures = factureRestController.getFactures();
        assertSame(factureList, actualFactures);
        assertTrue(actualFactures.isEmpty());
        verify(factureService).retrieveAllFactures();
    }

    @Test
    void testRetrieveFacture() {
        Long factureId = 1L;
        Facture facture = new Facture(); // Initialize a Facture instance

        when(factureService.retrieveFacture(factureId)).thenReturn(facture);
        Facture actualFacture = factureRestController.retrieveFacture(factureId);
        assertSame(facture, actualFacture);
        verify(factureService).retrieveFacture(factureId);
    }

    @Test
    void testAddFacture() {
        Facture facture = new Facture(); // Initialize a Facture instance

        when(factureService.addFacture(facture)).thenReturn(facture);
        Facture addedFacture = factureRestController.addFacture(facture);
        assertSame(facture, addedFacture);
        verify(factureService).addFacture(facture);
    }

    @Test
    void testCancelFacture() {
        Long factureId = 1L;
        doNothing().when(factureService).cancelFacture(factureId);
        factureRestController.cancelFacture(factureId);
        verify(factureService, times(1)).cancelFacture(factureId);
    }

    @Test
    void testGetFactureByFournisseur() {
        Long fournisseurId = 1L;
        List<Facture> factureList = new ArrayList<>();
        when(factureService.getFacturesByFournisseur(fournisseurId)).thenReturn(factureList);
        List<Facture> actualFactures = factureRestController.getFactureByFournisseur(fournisseurId);
        assertSame(factureList, actualFactures);
        assertTrue(actualFactures.isEmpty());
        verify(factureService).getFacturesByFournisseur(fournisseurId);
    }

    @Test
    void testAssignOperateurToFacture() {
        Long idOperateur = 1L;
        Long idFacture = 2L;
        doNothing().when(factureService).assignOperateurToFacture(idOperateur, idFacture);
        factureRestController.assignOperateurToFacture(idOperateur, idFacture);
        verify(factureService, times(1)).assignOperateurToFacture(idOperateur, idFacture);
    }

    @Test
    void testPourcentageRecouvrement() {
        Date startDate = new Date(); // Set a valid start date
        Date endDate = new Date();   // Set a valid end date

        when(factureService.pourcentageRecouvrement(startDate, endDate)).thenReturn(0.75f);

        float pourcentage = factureRestController.pourcentageRecouvrement(startDate, endDate);
        assertTrue(pourcentage == 0.75f);
        verify(factureService).pourcentageRecouvrement(startDate, endDate);
    }
}
