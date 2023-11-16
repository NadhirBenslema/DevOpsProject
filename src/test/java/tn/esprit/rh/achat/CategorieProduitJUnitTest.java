package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.services.CategorieProduitServiceImpl;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategorieProduitJUnitTest {

    @Autowired
    private CategorieProduitServiceImpl categorieProduitService;

    @Autowired
    private CategorieProduitRepository categorieProduitRepository;

    @Test
    void testAddCategorieProduit() {
        
        CategorieProduit categorieProduit = new CategorieProduit();
        categorieProduit.setCodeCategorie("CAT001");
        categorieProduit.setLibelleCategorie("Category A");

        
        CategorieProduit savedCategorieProduit = categorieProduitService.addCategorieProduit(categorieProduit);

        
        assertEquals(categorieProduit.getCodeCategorie(), savedCategorieProduit.getCodeCategorie());
        assertEquals(categorieProduit.getLibelleCategorie(), savedCategorieProduit.getLibelleCategorie());
    }

    @Test
    void testDeleteCategorieProduit() {
        
        CategorieProduit categorieProduitToDelete = new CategorieProduit();
        categorieProduitToDelete.setCodeCategorie("CAT002");
        categorieProduitToDelete.setLibelleCategorie("Category B");

        
        CategorieProduit savedCategorieProduit = categorieProduitRepository.save(categorieProduitToDelete);
        CategorieProduit dataBaseCheck = categorieProduitRepository.findById(savedCategorieProduit.getIdCategorieProduit()).orElse(null);
        System.out.println("Database check after saving:"+dataBaseCheck);
        
        categorieProduitService.deleteCategorieProduit(savedCategorieProduit.getIdCategorieProduit());

        
        CategorieProduit deletedCategorieProduit = categorieProduitRepository.findById(savedCategorieProduit.getIdCategorieProduit()).orElse(null);
        assertNull(deletedCategorieProduit);
    }

    @Test
    void testUpdateCategorieProduit() {
        
        CategorieProduit categorieProduitToUpdate = new CategorieProduit();
        categorieProduitToUpdate.setCodeCategorie("CAT003");
        categorieProduitToUpdate.setLibelleCategorie("Category C");

        
        CategorieProduit savedCategorieProduit = categorieProduitRepository.save(categorieProduitToUpdate);

        
        savedCategorieProduit.setLibelleCategorie("Updated Category C");

        
        CategorieProduit updatedCategorieProduit = categorieProduitService.updateCategorieProduit(savedCategorieProduit);

        
        assertEquals(savedCategorieProduit.getLibelleCategorie(), updatedCategorieProduit.getLibelleCategorie());
    }
}
