package tn.esprit.spring.kaddem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllDepartements() {
        Departement dep1 = new Departement(1, "Finance");
        Departement dep2 = new Departement(2, "IT");
        when(departementRepository.findAll()).thenReturn(Arrays.asList(dep1, dep2));

        List<Departement> result = departementService.retrieveAllDepartements();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testAddDepartement() {
        Departement dep = new Departement(null, "Human Resources");
        Departement savedDep = new Departement(1, "Human Resources");
        when(departementRepository.save(dep)).thenReturn(savedDep);

        Departement result = departementService.addDepartement(dep);

        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        assertEquals("Human Resources", result.getNomDepart());
        verify(departementRepository, times(1)).save(dep);
    }

    @Test
    void testUpdateDepartement() {
        Departement dep = new Departement(1, "Updated Department");
        when(departementRepository.save(dep)).thenReturn(dep);

        Departement result = departementService.updateDepartement(dep);

        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        assertEquals("Updated Department", result.getNomDepart());
        verify(departementRepository, times(1)).save(dep);
    }

    @Test
    void testRetrieveDepartement() {
        Departement dep = new Departement(1, "Finance");
        when(departementRepository.findById(1)).thenReturn(Optional.of(dep));

        Departement result = departementService.retrieveDepartement(1);

        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        assertEquals("Finance", result.getNomDepart());
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteDepartement() {
        Departement dep = new Departement(1, "Finance");
        when(departementRepository.findById(1)).thenReturn(Optional.of(dep));

        departementService.deleteDepartement(1);

        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).delete(dep);
    }
}
