package tn.esprit.rh.achat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.rh.achat.controllers.FactureRestController;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.services.IFactureService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class FactureTest {

    private MockMvc mockMvc;

    @Mock
    private IFactureService factureService;

    @InjectMocks
    private FactureRestController factureRestController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(factureRestController).build();
    }

    @Test
    public void testGetFactures() throws Exception {
        // Prepare test data
        List<Facture> factures = new ArrayList<>();
        factures.add(new Facture(/* initialize a Facture instance */));

        when(factureService.retrieveAllFactures()).thenReturn(factures);

        mockMvc.perform(get("/facture/retrieve-all-factures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(factureService, times(1)).retrieveAllFactures();
        verifyNoMoreInteractions(factureService);
    }

    @Test
    public void testRetrieveFacture() throws Exception {
        Long factureId = 1L;
        Facture facture = new Facture(/* initialize a Facture instance */);

        when(factureService.retrieveFacture(factureId)).thenReturn(facture);

        mockMvc.perform(get("/facture/retrieve-facture/{facture-id}", factureId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(factureService, times(1)).retrieveFacture(factureId);
        verifyNoMoreInteractions(factureService);
    }

    @Test
    public void testCancelFacture() throws Exception {
        Long factureId = 1L;

        mockMvc.perform(put("/facture/cancel-facture/{facture-id}", factureId))
                .andExpect(status().isOk());

        verify(factureService, times(1)).cancelFacture(factureId);
        verifyNoMoreInteractions(factureService);
    }

    @Test
    public void testGetFactureByFournisseur() throws Exception {
        Long fournisseurId = 1L;

        // Prepare test data
        List<Facture> factures = new ArrayList<>();
        factures.add(new Facture(/* initialize a Facture instance */));

        when(factureService.getFacturesByFournisseur(fournisseurId)).thenReturn(factures);

        mockMvc.perform(get("/facture/getFactureByFournisseur/{fournisseur-id}", fournisseurId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(factureService, times(1)).getFacturesByFournisseur(fournisseurId);
        verifyNoMoreInteractions(factureService);
    }

    @Test
    public void testAssignOperateurToFacture() throws Exception {
        Long idOperateur = 1L;
        Long idFacture = 2L;

        mockMvc.perform(put("/facture/assignOperateurToFacture/{idOperateur}/{idFacture}", idOperateur, idFacture))
                .andExpect(status().isOk());

        verify(factureService, times(1)).assignOperateurToFacture(idOperateur, idFacture);
        verifyNoMoreInteractions(factureService);
    }

   /* @Test
    public void testPourcentageRecouvrement() throws Exception {
        Date startDate = new Date(); // Set a valid start date
        Date endDate = new Date();   // Set a valid end date

        when(factureService.pourcentageRecouvrement(startDate, endDate)).thenReturn(0.75f);

        mockMvc.perform(get("/facture/pourcentageRecouvrement/{startDate}/{endDate}", startDate, endDate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("0.75"));

        verify(factureService, times(1)).pourcentageRecouvrement(startDate, endDate);
        verifyNoMoreInteractions(factureService);
    }

    */
}
