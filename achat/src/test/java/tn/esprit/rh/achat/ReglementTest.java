package tn.esprit.rh.achat;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ReglementServiceImpl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReglementTest {
    @InjectMocks
    private ReglementServiceImpl reglementService;

    @Mock
    private FactureRepository factureRepository;

    @Mock
    private ReglementRepository reglementRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllReglements() {
        List<Reglement> reglements = new ArrayList<>();
        when(reglementRepository.findAll()).thenReturn(reglements);

        List<Reglement> result = reglementService.retrieveAllReglements();

        assertEquals(0, result.size());
    }

    @Test
    public void testAddReglement() {
        Reglement reglement = new Reglement();
        when(reglementRepository.save(reglement)).thenReturn(reglement);

        Reglement result = reglementService.addReglement(reglement);

        assertNotNull(result);
    }

    @Test
    public void testRetrieveReglement() {
        Long reglementId = 1L;
        Reglement reglement = new Reglement();
        when(reglementRepository.findById(reglementId)).thenReturn(Optional.of(reglement));

        Reglement result = reglementService.retrieveReglement(reglementId);

        assertNotNull(result);
    }

    @Test
    public void testRetrieveReglementByFacture() {
        Long factureId = 1L;
        List<Reglement> reglements = new ArrayList<>();
        when(reglementRepository.retrieveReglementByFacture(factureId)).thenReturn(reglements);

        List<Reglement> result = reglementService.retrieveReglementByFacture(factureId);

        assertEquals(0, result.size());
    }

    @Test
    public void testGetChiffreAffaireEntreDeuxDate() {
        Date startDate = new Date();
        Date endDate = new Date();
        float chiffreAffaire = 1000.0f;
        when(reglementRepository.getChiffreAffaireEntreDeuxDate(startDate, endDate)).thenReturn(chiffreAffaire);

        float result = reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate);

        assertEquals(chiffreAffaire, result, 0.001);
    }
}
