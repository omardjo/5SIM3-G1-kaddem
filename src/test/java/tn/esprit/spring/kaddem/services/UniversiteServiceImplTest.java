package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;


import java.util.*;

@ExtendWith(SpringExtension.class)

public class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        universite = new Universite(1, "Université de Test");
    }

    @Test
    public void testAddUniversite() {
        when(universiteRepository.save(universite)).thenReturn(universite);
        Universite addedUniversite = universiteService.addUniversite(universite);
        assertNotNull(addedUniversite);
        assertEquals("Université de Test", addedUniversite.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testUpdateUniversite() {
        when(universiteRepository.save(universite)).thenReturn(universite);
        
        Universite updatedUniversite = universiteService.updateUniversite(universite);
        
        assertNotNull(updatedUniversite);
        assertEquals("Université de Test", updatedUniversite.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testRetrieveUniversite() {
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        Universite foundUniversite = universiteService.retrieveUniversite(1);
        assertNotNull(foundUniversite);
        assertEquals(1, foundUniversite.getIdUniv());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteUniversite() {
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        universiteService.deleteUniversite(1);
        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    public void testDeleteUniversiteNotFound() {
        when(universiteRepository.findById(1)).thenReturn(Optional.empty());

        universiteService.deleteUniversite(1);

        verify(universiteRepository, never()).delete(any(Universite.class));
    }

    @Test
    public void testRetrieveAllUniversites() {
        List<Universite> universites = Arrays.asList(universite);
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(universiteRepository, times(1)).findAll();
    }
}

