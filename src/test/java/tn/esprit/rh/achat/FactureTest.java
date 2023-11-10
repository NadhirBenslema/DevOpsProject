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
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.services.IFactureService;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        // Create a list of Facture objects (valid result)
        List<Facture> factureList = createFactureList();

        // Set up the mock behavior for a valid result
        when(factureService.retrieveAllFactures()).thenReturn(factureList);

        // Invoke the controller method for a valid result
        List<Facture> actualFactures = factureRestController.getFactures();

        // Logging for a valid result
        log.info("Testing with valid result...");
        log.info("Expected size: " + factureList.size());
        log.info("Actual size: " + actualFactures.size());

        // Assertions for a valid result
        assertSame(factureList, actualFactures);
        assertFalse(actualFactures.isEmpty()); // Ensure the list is not empty
        verify(factureService).retrieveAllFactures();

        // Reset the mock behavior
        reset(factureService);

        // Set up the mock behavior for no results found
        when(factureService.retrieveAllFactures()).thenReturn(Collections.emptyList());

        // Invoke the controller method for no results found
        actualFactures = factureRestController.getFactures();

        // Logging for no results found
        log.info("Testing with no results found...");
        log.info("Expected size: 0");
        log.info("Actual size: " + actualFactures.size());

        // Assertions for no results found
        assertNotNull(actualFactures);
        assertTrue(actualFactures.isEmpty()); // Ensure the list is empty
        verify(factureService, times(1)).retrieveAllFactures();

        // Reset the mock behavior
        reset(factureService);

        // Set up the mock behavior for an invalid result (e.g., null)
        when(factureService.retrieveAllFactures()).thenReturn(null);

        // Invoke the controller method for an invalid result
        actualFactures = factureRestController.getFactures();

        // Logging for an invalid result
        log.info("Testing with an invalid result...");
        log.info("Expected result: null");

        // Assertions for an invalid result
        assertNull(actualFactures); // Ensure the result is null
        verify(factureService, times(1)).retrieveAllFactures();
    }

    // Function to create a list of Facture objects
    private List<Facture> createFactureList() {
        List<Facture> factures = new ArrayList<>();

        // Sample data for Facture 1
        Facture facture1 = new Facture();
        facture1.setIdFacture(1L); // Set the ID or any other attributes you need
        facture1.setMontantFacture(1000); // Set the amount or other relevant attributes
        factures.add(facture1);

        // Sample data for Facture 2
        Facture facture2 = new Facture();
        facture2.setIdFacture(2L);
        facture2.setMontantFacture(200);
        factures.add(facture2);

        // You can add more Facture objects as needed

        return factures;
    }

    @Test
    void testRetrieveFacture() {
        Long factureId = 1L;
        Facture facture = new Facture(); // Initialize a Facture instance

        // Set up the mock behavior for a valid result
        when(factureService.retrieveFacture(factureId)).thenReturn(facture);

        // Invoke the controller method for a valid result
        Facture actualFacture = factureRestController.retrieveFacture(factureId);

        // Logging for a valid result
        log.info("Testing retrieveFacture with valid result...");
        log.info("Expected facture: " + facture);
        log.info("Actual facture: " + actualFacture);

        // Assertions for a valid result
        assertSame(facture, actualFacture);
        verify(factureService).retrieveFacture(factureId);

        // Reset the mock behavior
        reset(factureService);

        // Set up the mock behavior for no results found
        when(factureService.retrieveFacture(factureId)).thenReturn(null);

        // Invoke the controller method for no results found
        actualFacture = factureRestController.retrieveFacture(factureId);

        // Logging for no results found
        log.info("Testing retrieveFacture with no results found...");
        log.info("Expected result: null");
        log.info("Actual facture: " + actualFacture);

        // Assertions for no results found
        assertNull(actualFacture); // Ensure the result is null
        verify(factureService, times(1)).retrieveFacture(factureId);
    }

    @Test
    void testAddFacture() {
        // Create a valid facture object for a successful addition
        Facture validFacture = new Facture();

        // Set up the mock behavior for a successful addition
        when(factureService.addFacture(validFacture)).thenReturn(validFacture);

        // Invoke the controller method for a successful addition
        Facture addedFacture = factureRestController.addFacture(validFacture);

        // Logging for a successful addition
        log.info("Testing addFacture with valid data...");
        log.info("Expected result (valid addition): " + validFacture);
        log.info("Result of the valid addition: " + addedFacture);

        // Assertions for a successful addition
        assertSame(validFacture, addedFacture);
        verify(factureService).addFacture(validFacture);

        // Reset the mock behavior
        reset(factureService);

        // Create a facture object with invalid data (e.g., wrong data type for ID)
        Facture invalidFacture = new Facture();
        invalidFacture.setIdFacture(123L);

        // Set up the mock behavior for invalid data
        when(factureService.addFacture(invalidFacture)).thenReturn(null);

        // Invoke the controller method with invalid data
        Facture addedInvalidFacture = factureRestController.addFacture(invalidFacture);

        // Logging for the scenario with invalid data
        log.info("Testing addFacture with invalid data...");
        log.info("Expected result (invalid data): null");
        log.info("Result of the invalid addition: " + addedInvalidFacture);

        // Assertions for the scenario with invalid data
        assertNull(addedInvalidFacture);
        verify(factureService).addFacture(invalidFacture); // Ensure the service method is called with invalid data
    }

    @Test
    void testCancelFacture() {
        // Valid facture ID for a successful cancellation
        Long validFactureId = 1L;

        // Set up the mock behavior for a successful cancellation
        doNothing().when(factureService).cancelFacture(validFactureId);

        // Invoke the controller method for a successful cancellation
        factureRestController.cancelFacture(validFactureId);

        // Logging for a successful cancellation
        log.info("Testing cancelFacture with valid data...");
        log.info("Expected result (valid cancellation): Success");
        log.info("Result of the valid cancellation: Success");

        // Verify the service method is called for a successful cancellation
        verify(factureService, times(1)).cancelFacture(validFactureId);

        // Reset the mock behavior
        reset(factureService);

        // Invalid facture ID (e.g., null) for a scenario with invalid data
        Long invalidFactureId = null;

        // Set up the mock behavior for invalid data
        doAnswer(invocation -> {
            throw new IllegalArgumentException("Invalid facture ID");
        }).when(factureService).cancelFacture(invalidFactureId);

        // Invoke the controller method with invalid data
        try {
            factureRestController.cancelFacture(invalidFactureId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Logging for the scenario with invalid data
            log.info("Testing cancelFacture with invalid data...");
            log.info("Expected result (invalid data): IllegalArgumentException");
            log.info("Result of the invalid cancellation: IllegalArgumentException");

            // Verify the service method is called with invalid data
            verify(factureService, times(1)).cancelFacture(invalidFactureId);
        }

        // Reset the mock behavior
        reset(factureService);

        // Non-existent facture ID for a scenario when the ID is not found
        Long nonExistentFactureId = 99L;

        // Set up the mock behavior for a non-existent facture ID
        doThrow(new RuntimeException("Facture not found for ID: " + nonExistentFactureId))
                .when(factureService).cancelFacture(nonExistentFactureId);

        // Invoke the controller method with a non-existent facture ID
        try {
            factureRestController.cancelFacture(nonExistentFactureId);
            fail("Expected RuntimeException was not thrown.");
        } catch (RuntimeException e) {
            // Logging for the scenario when the ID is not found
            log.info("Testing cancelFacture when the ID is not found...");
            log.info("Expected result (ID not found): RuntimeException");
            log.info("Result of the cancellation when ID is not found: RuntimeException");

            // Verify the service method is called with the non-existent facture ID
            verify(factureService, times(1)).cancelFacture(nonExistentFactureId);
        }
    }

    @Test
    void testGetFactureByFournisseur() {
        // Valid fournisseur ID for a successful retrieval
        Long validFournisseurId = 1L;

        // Set up the mock behavior for a successful retrieval
        List<Facture> factureList = new ArrayList<>();
        when(factureService.getFacturesByFournisseur(validFournisseurId)).thenReturn(factureList);

        // Invoke the controller method for a successful retrieval
        List<Facture> actualFactures = factureRestController.getFactureByFournisseur(validFournisseurId);

        // Logging for a successful retrieval
        log.info("Testing getFactureByFournisseur with valid data...");
        log.info("Expected result (valid retrieval): " + factureList);
        log.info("Result of the valid retrieval: " + actualFactures);

        // Assertions for a successful retrieval
        assertSame(factureList, actualFactures);
        assertTrue(actualFactures.isEmpty());
        verify(factureService).getFacturesByFournisseur(validFournisseurId);

        // Reset the mock behavior
        reset(factureService);

        // Invalid fournisseur ID (e.g., null) for a scenario with invalid data
        Long invalidFournisseurId = null;

        // Set up the mock behavior for invalid data
        when(factureService.getFacturesByFournisseur(invalidFournisseurId))
                .thenThrow(new IllegalArgumentException("Invalid fournisseur ID"));

        // Invoke the controller method with invalid data
        try {
            factureRestController.getFactureByFournisseur(invalidFournisseurId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Logging for the scenario with invalid data
            log.info("Testing getFactureByFournisseur with invalid data...");
            log.info("Expected result (invalid data): IllegalArgumentException");
            log.info("Result of the invalid retrieval: IllegalArgumentException");

            // Verify the service method is called with invalid data
            verify(factureService, times(1)).getFacturesByFournisseur(invalidFournisseurId);
        }

        // Reset the mock behavior
        reset(factureService);

        // Non-existent fournisseur ID for a scenario when the fournisseur is not found
        Long nonExistentFournisseurId = 99L;

        // Set up the mock behavior for a non-existent fournisseur ID
        when(factureService.getFacturesByFournisseur(nonExistentFournisseurId)).thenReturn(Collections.emptyList());

        // Invoke the controller method with a non-existent fournisseur ID
        List<Facture> nonExistentFactures = factureRestController.getFactureByFournisseur(nonExistentFournisseurId);

        // Logging for the scenario when the fournisseur is not found
        log.info("Testing getFactureByFournisseur when the fournisseur is not found...");
        log.info("Expected result (fournisseur not found): Empty List");
        log.info("Result of the retrieval when fournisseur is not found: " + nonExistentFactures);

        // Assertions for the scenario when the fournisseur is not found
        assertNotNull(nonExistentFactures);
        assertTrue(nonExistentFactures.isEmpty());
        verify(factureService, times(1)).getFacturesByFournisseur(nonExistentFournisseurId);

        // Reset the mock behavior
        reset(factureService);

        // Other scenarios can be added based on your specific use cases
    }

    @Test
    void testAssignOperateurToFacture() {
        // Valid operateur and facture IDs for a successful assignment
        Long validOperateurId = 1L;
        Long validFactureId = 2L;

        // Set up the mock behavior for a successful assignment
        doNothing().when(factureService).assignOperateurToFacture(validOperateurId, validFactureId);

        // Invoke the controller method for a successful assignment
        factureRestController.assignOperateurToFacture(validOperateurId, validFactureId);

        // Logging for a successful assignment
        log.info("Testing assignOperateurToFacture with valid data...");
        log.info("Expected result (valid assignment): Success");

        // Verify the service method is called for a successful assignment
        verify(factureService, times(1)).assignOperateurToFacture(validOperateurId, validFactureId);

        // Reset the mock behavior
        reset(factureService);

        // Invalid operateur ID (e.g., null) for a scenario with invalid data
        Long invalidOperateurId = null;

        // Set up the mock behavior for invalid operateur ID
        doAnswer(invocation -> {
            throw new IllegalArgumentException("Invalid operateur ID");
        }).when(factureService).assignOperateurToFacture(invalidOperateurId, validFactureId);

        // Invoke the controller method with invalid operateur ID
        try {
            factureRestController.assignOperateurToFacture(invalidOperateurId, validFactureId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Logging for the scenario with invalid operateur ID
            log.info("Testing assignOperateurToFacture with invalid operateur ID...");
            log.info("Expected result (invalid operateur ID): IllegalArgumentException");
            log.info("Result of the invalid assignment: IllegalArgumentException");

            // Verify the service method is called with invalid operateur ID
            verify(factureService, times(1)).assignOperateurToFacture(invalidOperateurId, validFactureId);
        }

        // Reset the mock behavior
        reset(factureService);

        // Invalid facture ID (e.g., null) for a scenario with invalid data
        Long invalidFactureId = null;

        // Set up the mock behavior for invalid facture ID
        doAnswer(invocation -> {
            throw new IllegalArgumentException("Invalid facture ID");
        }).when(factureService).assignOperateurToFacture(validOperateurId, invalidFactureId);

        // Invoke the controller method with invalid facture ID
        try {
            factureRestController.assignOperateurToFacture(validOperateurId, invalidFactureId);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Logging for the scenario with invalid facture ID
            log.info("Testing assignOperateurToFacture with invalid facture ID...");
            log.info("Expected result (invalid facture ID): IllegalArgumentException");
            log.info("Result of the invalid assignment: IllegalArgumentException");

            // Verify the service method is called with invalid facture ID
            verify(factureService, times(1)).assignOperateurToFacture(validOperateurId, invalidFactureId);
        }

        // Reset the mock behavior
        reset(factureService);

        // Other scenarios can be added based on your specific use cases
    }



}