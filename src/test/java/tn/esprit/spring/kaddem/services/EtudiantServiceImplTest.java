package tn.esprit.spring.kaddem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import tn.esprit.spring.kaddem.entities.*;
import tn.esprit.spring.kaddem.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class EtudiantServiceImplTest {

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Etudiant etudiant; // Added variable for specific tests
    private Contrat contrat;
    private Equipe equipe;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Etudiant instances
        etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1);
        etudiant1.setNomE("John Doe");
        
        etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2);
        etudiant2.setNomE("Jane Smith");
        
        etudiant = new Etudiant(); // Initialize the new etudiant variable
        etudiant.setIdEtudiant(3);
        etudiant.setNomE("Alice Johnson");
        
        // Initialize Contrat instance
        contrat = new Contrat();
        contrat.setIdContrat(1);
        
        // Initialize Equipe instance with a HashSet to match Set<Etudiant>
        equipe = new Equipe();
        equipe.setIdEquipe(1);
        equipe.setEtudiants(new HashSet<>()); // Change from ArrayList to HashSet
    }

    @Test
    public void testRetrieveAllEtudiants() {
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1, etudiant2));

        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

        assertNotNull(etudiants);
        assertEquals(2, etudiants.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    public void testAddEtudiant() {
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant1);

        assertNotNull(savedEtudiant);
        assertEquals("John Doe", savedEtudiant.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant1);
    }

    @Test
    public void testUpdateEtudiant() {
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant1);

        assertNotNull(updatedEtudiant);
        assertEquals("John Doe", updatedEtudiant.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant1);
    }

    @Test
    public void testRetrieveEtudiant() {
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant1));

        Etudiant foundEtudiant = etudiantService.retrieveEtudiant(1);

        assertNotNull(foundEtudiant);
        assertEquals("John Doe", foundEtudiant.getNomE());
        verify(etudiantRepository, times(1)).findById(1);
    }

    @Test
    public void testRemoveEtudiant() {
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant1));

        etudiantService.removeEtudiant(1);

        verify(etudiantRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).delete(etudiant1);
    }

    @Test
    public void testAssignEtudiantToDepartement() {
        Departement departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Informatique");

        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant1));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        etudiantService.assignEtudiantToDepartement(1, 1);

        assertEquals(departement, etudiant1.getDepartement());
        verify(etudiantRepository, times(1)).save(etudiant1);
    }

    @Test
    public void testGetEtudiantsByDepartement() {
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(1)).thenReturn(Arrays.asList(etudiant1));

        List<Etudiant> etudiants = etudiantService.getEtudiantsByDepartement(1);

        assertNotNull(etudiants);
        assertEquals(1, etudiants.size());
        assertEquals("John Doe", etudiants.get(0).getNomE());
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(1);
    }
    
    @Test
    public void testAddAndAssignEtudiantToEquipeAndContract_Success() {
        // Arrange: Mock contract and team exist
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act: Call the method
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Assert: Validate successful assignment
        assertNotNull(result);
        assertEquals(etudiant, contrat.getEtudiant());
        assertTrue(equipe.getEtudiants().contains(etudiant));
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    public void testAddAndAssignEtudiantToEquipeAndContract_ContratNotFound() {
        // Arrange: Mock contract not found
        when(contratRepository.findById(1)).thenReturn(Optional.empty());
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        // Act: Call the method
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Assert: Validate that the result is null (failure)
        assertNull(result);
        verify(etudiantRepository, never()).save(any(Etudiant.class));
    }

    @Test
    public void testAddAndAssignEtudiantToEquipeAndContract_EquipeNotFound() {
        // Arrange: Mock team not found
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(1)).thenReturn(Optional.empty());

        // Act: Call the method
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Assert: Validate that the result is null (failure)
        assertNull(result);
        verify(etudiantRepository, never()).save(any(Etudiant.class));
    }

    @Test
    public void testAddAndAssignEtudiantToEquipeAndContract_BothNotFound() {
        // Arrange: Mock both contract and team not found
        when(contratRepository.findById(1)).thenReturn(Optional.empty());
        when(equipeRepository.findById(1)).thenReturn(Optional.empty());

        // Act: Call the method
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Assert: Validate that the result is null (failure)
        assertNull(result);
        verify(etudiantRepository, never()).save(any(Etudiant.class));
    }
}

