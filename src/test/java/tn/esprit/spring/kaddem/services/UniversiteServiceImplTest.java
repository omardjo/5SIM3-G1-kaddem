import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteServiceImplTest {

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    DepartementRepository departementRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    Universite universite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        universite = new Universite(1, "Université de Test");
    }

    @Test
    void testAddUniversite() {
        when(universiteRepository.save(universite)).thenReturn(universite);
        Universite addedUniversite = universiteService.addUniversite(universite);
        assertNotNull(addedUniversite);
        assertEquals("Université de Test", addedUniversite.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        Universite foundUniversite = universiteService.retrieveUniversite(1);
        assertNotNull(foundUniversite);
        assertEquals(1, foundUniversite.getIdUniv());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteUniversite() {
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        universiteService.deleteUniversite(1);
        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        Departement departement = new Departement(1, "Informatique");
        Set<Departement> departements = new HashSet<>();
        departements.add(departement);
        universite.setDepartements(departements);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        universiteService.assignUniversiteToDepartement(1, 1);

        verify(universiteRepository, times(1)).save(universite);
        assertTrue(universite.getDepartements().contains(departement));
    }
}

