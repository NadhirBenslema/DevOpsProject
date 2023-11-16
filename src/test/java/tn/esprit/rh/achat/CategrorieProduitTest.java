package tn.esprit.rh.achat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.controllers.CategorieProduitController;
import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.services.ICategorieProduitService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = { CategorieProduit.class })
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "tn.esprit.rh.achat.controllers")
public class CategrorieProduitTest {
    private static final Logger log = LoggerFactory.getLogger(CategrorieProduitTest.class);
    @MockBean
    private CategorieProduitRepository categorieProduitRepository;

    @MockBean
    private ICategorieProduitService categorieProduitService;

    @Test

    void testRetrieveAllCategorieProduits() {
        
        List<CategorieProduit> categorieProduitList = createCategorieProduitList();

        
        when(categorieProduitService.retrieveAllCategorieProduits()).thenReturn(categorieProduitList);

        
        List<CategorieProduit> actualCategorie = categorieProduitService.retrieveAllCategorieProduits();

        
        log.info("Testing with valid result...");
        log.info("Expected size: " + categorieProduitList.size());
        log.info("Actual size: " + actualCategorie.size());

        
        assertEquals(categorieProduitList.size(), actualCategorie.size());
        assertEquals(categorieProduitList, actualCategorie);
        assertFalse(actualCategorie.isEmpty()); 
        verify(categorieProduitService).retrieveAllCategorieProduits();

        reset(categorieProduitService);

        
        when(categorieProduitService.retrieveAllCategorieProduits()).thenReturn(Collections.emptyList());

        
         actualCategorie = categorieProduitService.retrieveAllCategorieProduits();

        
        log.info("Testing with no results found...");
        log.info("Expected size: 0");
        log.info("Actual size: " + actualCategorie.size());

        
        assertNotNull(actualCategorie);
        assertTrue(actualCategorie.isEmpty()); 
        verify(categorieProduitService, times(1)).retrieveAllCategorieProduits();

        
        reset(categorieProduitService);

        
        when(categorieProduitService.retrieveAllCategorieProduits()).thenReturn(null);

        
        actualCategorie = categorieProduitService.retrieveAllCategorieProduits();

        
        log.info("Testing with an invalid result...");
        log.info("Expected result: null");
        log.info("Actual result:" +actualCategorie);
        
        assertNull(actualCategorie); 
        verify(categorieProduitService, times(1)).retrieveAllCategorieProduits();
    }

    private List<CategorieProduit> createCategorieProduitList() {

        CategorieProduit categorieProduit1 = new CategorieProduit();
        categorieProduit1.setIdCategorieProduit(1L);
        categorieProduit1.setCodeCategorie("CAT001");
        categorieProduit1.setLibelleCategorie("Category A");

        CategorieProduit categorieProduit2 = new CategorieProduit();
        categorieProduit2.setIdCategorieProduit(2L);
        categorieProduit2.setCodeCategorie("CAT002");
        categorieProduit2.setLibelleCategorie("Category B");

        return Arrays.asList(categorieProduit1, categorieProduit2);
    }


    @Test
    void testRetrieveCategorieProduit() {

        CategorieProduit expectedCategorie = createCategorieProduit();


        Long categorieProduitId = 1L;
        when(categorieProduitService.retrieveCategorieProduit(categorieProduitId)).thenReturn(expectedCategorie);


        CategorieProduit actualCategorie = categorieProduitService.retrieveCategorieProduit(categorieProduitId);

        
        log.info("Testing with valid result...");
        log.info("Expected result: " + expectedCategorie);
        log.info("Actual result: " + actualCategorie);


        assertEquals(expectedCategorie, actualCategorie);
        verify(categorieProduitService).retrieveCategorieProduit(categorieProduitId);


        reset(categorieProduitService);

        when(categorieProduitService.retrieveCategorieProduit(categorieProduitId)).thenReturn(null);


        actualCategorie = categorieProduitService.retrieveCategorieProduit(categorieProduitId);


        log.info("Testing with no result found...");
        log.info("Expected result: null");
        log.info("Actual result: " + actualCategorie);


        assertNull(actualCategorie);
        verify(categorieProduitService, times(1)).retrieveCategorieProduit(categorieProduitId);


        reset(categorieProduitService);


        when(categorieProduitService.retrieveCategorieProduit(categorieProduitId)).thenReturn(null);


        actualCategorie = categorieProduitService.retrieveCategorieProduit(categorieProduitId);


        log.info("Testing with an invalid result...");
        log.info("Expected result: null");
        log.info("Actual result:" + actualCategorie);


        assertNull(actualCategorie);
        verify(categorieProduitService, times(1)).retrieveCategorieProduit(categorieProduitId);
    }

    private CategorieProduit createCategorieProduit() {

        CategorieProduit categorieProduit1 = new CategorieProduit();
        categorieProduit1.setIdCategorieProduit(1L);
        categorieProduit1.setCodeCategorie("CAT001");
        categorieProduit1.setLibelleCategorie("Category A");

        return categorieProduit1;
    }

}
