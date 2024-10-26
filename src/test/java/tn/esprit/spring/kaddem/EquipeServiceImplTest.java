package tn.esprit.spring.kaddem.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipeServiceImplTest {
    @Mock
    private EquipeRepository equipeRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllEquipes() {
        List<Equipe> equipes = new ArrayList<>();
        Equipe equipe1 = new Equipe("Equipe 1");
        Equipe equipe2 = new Equipe("Equipe 2");
        equipes.add(equipe1);
        equipes.add(equipe2);

        when(equipeRepository.findAll()).thenReturn(equipes);

        List<Equipe> result = equipeService.retrieveAllEquipes();
        assertEquals(2, result.size());
        assertEquals("Equipe 1", result.get(0).getNomEquipe());
    }

    @Test
    public void testAddEquipe() {
        Equipe equipe = new Equipe("New Equipe");
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe result = equipeService.addEquipe(equipe);
        assertEquals("New Equipe", result.getNomEquipe());
    }

    @Test
    public void testDeleteEquipe() {
        Equipe equipe = new Equipe("Equipe to delete");
        equipe.setIdEquipe(1);
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        equipeService.deleteEquipe(1);
        verify(equipeRepository, times(1)).delete(equipe);
    }

    @Test
    public void testRetrieveEquipe() {
        Equipe equipe = new Equipe("Single Equipe");
        equipe.setIdEquipe(1);
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        Equipe result = equipeService.retrieveEquipe(1);
        assertEquals("Single Equipe", result.getNomEquipe());
    }

    @Test
    public void testUpdateEquipe() {
        Equipe equipe = new Equipe("Updated Equipe");
        equipe.setIdEquipe(1);
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe result = equipeService.updateEquipe(equipe);
        assertEquals("Updated Equipe", result.getNomEquipe());
    }
}
