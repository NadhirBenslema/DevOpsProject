package tn.esprit.rh.achat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;

import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FournisseurTest {

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private DetailFournisseurRepository detailFournisseurRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testRetrieveAllFournisseurs() {
        when(fournisseurRepository.findAll()).thenReturn(Arrays.asList(new Fournisseur(), new Fournisseur()));

        List<Fournisseur> fournisseurs = fournisseurService.retrieveAllFournisseurs();

        assertEquals(2, fournisseurs.size());
    }





    @Test
    public void testAddFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);

        when(detailFournisseurRepository.save(any(DetailFournisseur.class))).thenReturn(detailFournisseur);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        Fournisseur result = fournisseurService.addFournisseur(fournisseur);

        assertNotNull(result);
    }


    @Test
    public void testUpdateFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);

        when(detailFournisseurRepository.save(any(DetailFournisseur.class))).thenReturn(detailFournisseur);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        Fournisseur result = fournisseurService.updateFournisseur(fournisseur);

        assertNotNull(result);
        assertEquals(detailFournisseur, result.getDetailFournisseur());
    }


}
