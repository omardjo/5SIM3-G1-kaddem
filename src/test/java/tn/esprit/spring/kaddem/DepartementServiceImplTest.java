import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

@ActiveProfiles("test3")
@ExtendWith(MockitoExtension.class)
public class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    private Departement departement;

    @BeforeEach
    public void setUp() {
        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("IT");
    }

    @Test
    public void testRetrieveAllDepartements() {
        when(departementRepository.findAll()).thenReturn(Arrays.asList(departement));

        List<Departement> result = departementService.retrieveAllDepartements();
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getNomDepart());
    }

    @Test
    public void testAddDepartement() {
        when(departementRepository.save(departement)).thenReturn(departement);

        Departement result = departementService.addDepartement(departement);
        assertEquals(departement, result);
    }

    @Test
    public void testUpdateDepartement() {
        when(departementRepository.existsById(departement.getIdDepart())).thenReturn(true);
        when(departementRepository.save(departement)).thenReturn(departement);

        Departement result = departementService.updateDepartement(departement);
        assertEquals(departement, result);
    }

    @Test
    public void testUpdateDepartementNotFound() {
        when(departementRepository.existsById(departement.getIdDepart())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            departementService.updateDepartement(departement);
        });
    }

    @Test
    public void testRetrieveDepartement() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        Departement result = departementService.retrieveDepartement(1);
        assertEquals(departement, result);
    }

    @Test
    public void testRetrieveDepartementNotFound() {
        when(departementRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            departementService.retrieveDepartement(1);
        });
    }

    @Test
    public void testDeleteDepartement() {
        when(departementRepository.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> departementService.deleteDepartement(1));
        verify(departementRepository).deleteById(1);
    }

    @Test
    public void testDeleteDepartementNotFound() {
        when(departementRepository.existsById(1)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            departementService.deleteDepartement(1);
        });
    }
}

