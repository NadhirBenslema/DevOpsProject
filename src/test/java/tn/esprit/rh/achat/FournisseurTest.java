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

import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;

import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {FournisseurServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class FournisseurTest {




    @MockBean
    private FournisseurRepository fournisseurRepository;

    @MockBean
    private DetailFournisseurRepository detailFournisseurRepository;

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


}
