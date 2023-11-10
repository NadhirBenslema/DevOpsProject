package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
@SpringBootTest
@ContextConfiguration(classes = {OperateurServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class OperateurTest {

    @MockBean
    private FactureRepository factureRepository;

    @MockBean
    private OperateurRepository operateurRepository;

    @Autowired
    private OperateurServiceImpl operateurServiceImpl;

    @Test
    void testRetrieveAllOperateurs() {
        ArrayList<Operateur> operateurList = new ArrayList<>();
        when(operateurRepository.findAll()).thenReturn(operateurList);
        List<Operateur> actualRetrieveAllOperateursResult = operateurServiceImpl.retrieveAllOperateurs();
        assertSame(operateurList, actualRetrieveAllOperateursResult);
        assertTrue(actualRetrieveAllOperateursResult.isEmpty());
        verify(operateurRepository).findAll();
    }

    @Test
    void testDeleteOperateurs() {
        doNothing().when(operateurRepository).deleteById((Long) any());
        operateurServiceImpl.deleteOperateur(123L);
        verify(operateurRepository).deleteById((Long) any());
    }

    @Test
    void testAddOperateur() {
        Operateur operateurToAdd = new Operateur();
        when(operateurRepository.save(operateurToAdd)).thenReturn(operateurToAdd);

        Operateur addedOperateur = operateurServiceImpl.addOperateur(operateurToAdd);

        assertEquals(operateurToAdd, addedOperateur);
        verify(operateurRepository).save(operateurToAdd);
    }

    @Test
    void testUpdateOperateur() {
        Operateur operateurToUpdate = new Operateur();
        when(operateurRepository.save(operateurToUpdate)).thenReturn(operateurToUpdate);

        Operateur updatedOperateur = operateurServiceImpl.updateOperateur(operateurToUpdate);

        assertEquals(operateurToUpdate, updatedOperateur);
        verify(operateurRepository).save(operateurToUpdate);
    }

    @Test
    void testRetrieveOperateur() {
        long operateurId = 123L;
        Operateur expectedOperateur = new Operateur();
        when(operateurRepository.findById(operateurId)).thenReturn(java.util.Optional.of(expectedOperateur));

        Operateur retrievedOperateur = operateurServiceImpl.retrieveOperateur(operateurId);

        assertEquals(expectedOperateur, retrievedOperateur);
        verify(operateurRepository).findById(operateurId);
    }

}
