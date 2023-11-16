package tn.esprit.rh.achat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.controllers.FactureRestController;
import tn.esprit.rh.achat.entities.DetailFacture;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.services.IFactureService;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = { FactureRestController.class })
@ExtendWith(SpringExtension.class)
public class FactureTest {
    private static final Logger log = LoggerFactory.getLogger(FactureTest.class);
    @MockBean
    private IFactureService factureService;

    @Autowired
    private FactureRestController factureRestController;
    @MockBean
    private FactureRepository factureRepository;

    @MockBean
    private ReglementServiceImpl reglementServiceImpl;

    @Test
    void testRetrieveAllFactures() {
        
        List<Facture> factureList = createFactureList();

        
        when(factureService.retrieveAllFactures()).thenReturn(factureList);

        
        List<Facture> actualFactures = factureRestController.getFactures();

        
        log.info("Testing with valid result...");
        log.info("Expected size: " + factureList.size());
        log.info("Actual size: " + actualFactures.size());

        
        assertSame(factureList, actualFactures);
        assertFalse(actualFactures.isEmpty()); 
        verify(factureService).retrieveAllFactures();

        
        reset(factureService);

        
        when(factureService.retrieveAllFactures()).thenReturn(Collections.emptyList());

        
        actualFactures = factureRestController.getFactures();

        
        log.info("Testing with no results found...");
        log.info("Expected size: 0");
        log.info("Actual size: " + actualFactures.size());

        
        assertNotNull(actualFactures);
        assertTrue(actualFactures.isEmpty()); 
        verify(factureService, times(1)).retrieveAllFactures();

        
        reset(factureService);

        
        when(factureService.retrieveAllFactures()).thenReturn(null);

        
        actualFactures = factureRestController.getFactures();

        
        log.info("Testing with an invalid result...");
        log.info("Expected result: null");

        
        assertNull(actualFactures); 
        verify(factureService, times(1)).retrieveAllFactures();
    }

    
    private List<Facture> createFactureList() {
        List<Facture> factures = new ArrayList<>();

        
        Facture facture1 = new Facture();
        facture1.setIdFacture(1L); 
        facture1.setMontantFacture(1000); 
        factures.add(facture1);

        
        Facture facture2 = new Facture();
        facture2.setIdFacture(2L);
        facture2.setMontantFacture(200);
        factures.add(facture2);

        

        return factures;
    }

    @Test
    void testRetrieveFacture() {
        Long factureId = 1L;
        Facture facture = new Facture(); 

        
        when(factureService.retrieveFacture(factureId)).thenReturn(facture);

        
        Facture actualFacture = factureRestController.retrieveFacture(factureId);

        
        log.info("Testing retrieveFacture with valid result...");
        log.info("Expected facture: " + facture);
        log.info("Actual facture: " + actualFacture);

        
        assertSame(facture, actualFacture);
        verify(factureService).retrieveFacture(factureId);

        
        reset(factureService);

        
        when(factureService.retrieveFacture(factureId)).thenReturn(null);

        
        actualFacture = factureRestController.retrieveFacture(factureId);

        
        log.info("Testing retrieveFacture with no results found...");
        log.info("Expected result: null");
        log.info("Actual facture: " + actualFacture);

        
        assertNull(actualFacture); 
        verify(factureService, times(1)).retrieveFacture(factureId);
    }

    @Test
    void testAddFacture() {
        
        Facture validFacture = new Facture();

        
        when(factureService.addFacture(validFacture)).thenReturn(validFacture);

        
        Facture addedFacture = factureRestController.addFacture(validFacture);

        
        log.info("Testing addFacture with valid data...");
        log.info("Expected result (valid addition): " + validFacture);
        log.info("Result of the valid addition: " + addedFacture);

        
        assertSame(validFacture, addedFacture);
        verify(factureService).addFacture(validFacture);

        
        reset(factureService);

        
        Facture invalidFacture = new Facture();
        invalidFacture.setIdFacture(123L);

        
        when(factureService.addFacture(invalidFacture)).thenReturn(null);

        
        Facture addedInvalidFacture = factureRestController.addFacture(invalidFacture);

        
        log.info("Testing addFacture with invalid data...");
        log.info("Expected result (invalid data): null");
        log.info("Result of the invalid addition: " + addedInvalidFacture);

        
        assertNull(addedInvalidFacture);
        verify(factureService).addFacture(invalidFacture); 
    }

    @Test
    void testCancelFacture() {
        
        Long validFactureId = 1L;

        
        doNothing().when(factureService).cancelFacture(validFactureId);

        
        factureRestController.cancelFacture(validFactureId);

        
        log.info("Testing cancelFacture with valid data...");
        log.info("Expected result (valid cancellation): Success");
        log.info("Result of the valid cancellation: Success");

        
        verify(factureService, times(1)).cancelFacture(validFactureId);

        
        reset(factureService);

        
        Long invalidFactureId = null;

        
        doAnswer(invocation -> {
            throw new IllegalArgumentException("Invalid facture ID");
        }).when(factureService).cancelFacture(invalidFactureId);

        
        try {
            factureRestController.cancelFacture(invalidFactureId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            
            log.info("Testing cancelFacture with invalid data...");
            log.info("Expected result (invalid data): IllegalArgumentException");
            log.info("Result of the invalid cancellation: IllegalArgumentException");

            
            verify(factureService, times(1)).cancelFacture(invalidFactureId);
        }

        
        reset(factureService);

        
        Long nonExistentFactureId = 99L;

        
        doThrow(new RuntimeException("Facture not found for ID: " + nonExistentFactureId))
                .when(factureService).cancelFacture(nonExistentFactureId);

        
        try {
            factureRestController.cancelFacture(nonExistentFactureId);
            fail("Expected RuntimeException was not thrown.");
        } catch (RuntimeException e) {
            
            log.info("Testing cancelFacture when the ID is not found...");
            log.info("Expected result (ID not found): RuntimeException");
            log.info("Result of the cancellation when ID is not found: RuntimeException");

            
            verify(factureService, times(1)).cancelFacture(nonExistentFactureId);
        }
    }

    @Test
    void testGetFactureByFournisseur() {
        
        Long validFournisseurId = 1L;

        
        List<Facture> factureList = new ArrayList<>();
        when(factureService.getFacturesByFournisseur(validFournisseurId)).thenReturn(factureList);

        
        List<Facture> actualFactures = factureRestController.getFactureByFournisseur(validFournisseurId);

        
        log.info("Testing getFactureByFournisseur with valid data...");
        log.info("Expected result (valid retrieval): " + factureList);
        log.info("Result of the valid retrieval: " + actualFactures);

        
        assertSame(factureList, actualFactures);
        assertTrue(actualFactures.isEmpty());
        verify(factureService).getFacturesByFournisseur(validFournisseurId);

        
        reset(factureService);

        
        Long invalidFournisseurId = null;

        
        when(factureService.getFacturesByFournisseur(invalidFournisseurId))
                .thenThrow(new IllegalArgumentException("Invalid fournisseur ID"));

        
        try {
            factureRestController.getFactureByFournisseur(invalidFournisseurId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            
            log.info("Testing getFactureByFournisseur with invalid data...");
            log.info("Expected result (invalid data): IllegalArgumentException");
            log.info("Result of the invalid retrieval: IllegalArgumentException");

            
            verify(factureService, times(1)).getFacturesByFournisseur(invalidFournisseurId);
        }

        
        reset(factureService);

        
        Long nonExistentFournisseurId = 99L;

        
        when(factureService.getFacturesByFournisseur(nonExistentFournisseurId)).thenReturn(Collections.emptyList());

        
        List<Facture> nonExistentFactures = factureRestController.getFactureByFournisseur(nonExistentFournisseurId);

        
        log.info("Testing getFactureByFournisseur when the fournisseur is not found...");
        log.info("Expected result (fournisseur not found): Empty List");
        log.info("Result of the retrieval when fournisseur is not found: " + nonExistentFactures);

        
        assertNotNull(nonExistentFactures);
        assertTrue(nonExistentFactures.isEmpty());
        verify(factureService, times(1)).getFacturesByFournisseur(nonExistentFournisseurId);

        
        reset(factureService);

        
    }

    @Test
    void testAssignOperateurToFacture() {
        
        Long validOperateurId = 1L;
        Long validFactureId = 2L;

        
        doNothing().when(factureService).assignOperateurToFacture(validOperateurId, validFactureId);

        
        factureRestController.assignOperateurToFacture(validOperateurId, validFactureId);

        
        log.info("Testing assignOperateurToFacture with valid data...");
        log.info("Expected result (valid assignment): Success");

        
        verify(factureService, times(1)).assignOperateurToFacture(validOperateurId, validFactureId);

        
        reset(factureService);

        
        Long invalidOperateurId = null;

        
        doAnswer(invocation -> {
            throw new IllegalArgumentException("Invalid operateur ID");
        }).when(factureService).assignOperateurToFacture(invalidOperateurId, validFactureId);

        
        try {
            factureRestController.assignOperateurToFacture(invalidOperateurId, validFactureId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            
            log.info("Testing assignOperateurToFacture with invalid operateur ID...");
            log.info("Expected result (invalid operateur ID): IllegalArgumentException");
            log.info("Result of the invalid assignment: IllegalArgumentException");

            
            verify(factureService, times(1)).assignOperateurToFacture(invalidOperateurId, validFactureId);
        }

        
        reset(factureService);

        
        Long invalidFactureId = null;

        
        doAnswer(invocation -> {
            throw new IllegalArgumentException("Invalid facture ID");
        }).when(factureService).assignOperateurToFacture(validOperateurId, invalidFactureId);

        
        try {
            factureRestController.assignOperateurToFacture(validOperateurId, invalidFactureId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            
            log.info("Testing assignOperateurToFacture with invalid facture ID...");
            log.info("Expected result (invalid facture ID): IllegalArgumentException");
            log.info("Result of the invalid assignment: IllegalArgumentException");

            
            verify(factureService, times(1)).assignOperateurToFacture(validOperateurId, invalidFactureId);
        }

        
        reset(factureService);

        
    }

    @Test
    void testGetDetailsForFacture() {
        
        Long validFactureId = 1L;

        
        Set<DetailFacture> detailsFacture = new HashSet<>();
        when(factureService.getDetailsForFacture(validFactureId)).thenReturn(detailsFacture);

        
        Set<DetailFacture> actualDetails = factureRestController.getDetailsForFacture(validFactureId);

        
        log.info("Testing getDetailsForFacture with valid data...");
        log.info("Expected result (valid retrieval): " + detailsFacture);
        log.info("Result of the valid retrieval: " + actualDetails);

        
        assertSame(detailsFacture, actualDetails);
        verify(factureService).getDetailsForFacture(validFactureId);

        
        reset(factureService);

        
        Long invalidFactureId = null;

        
        when(factureService.getDetailsForFacture(invalidFactureId))
                .thenThrow(new EntityNotFoundException("Facture not found with ID: " + invalidFactureId));

        
        try {
            factureRestController.getDetailsForFacture(invalidFactureId);
            fail("Expected EntityNotFoundException was not thrown.");
        } catch (EntityNotFoundException e) {
            
            log.info("Testing getDetailsForFacture with invalid data...");
            log.info("Expected result (invalid data): EntityNotFoundException");
            log.info("Result of the invalid retrieval: EntityNotFoundException");

            
            verify(factureService, times(1)).getDetailsForFacture(invalidFactureId);
        }

        
        reset(factureService);
    }



}