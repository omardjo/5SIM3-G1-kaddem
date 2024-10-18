package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService {

    @Autowired
    EtudiantRepository etudiantRepository;

    @Autowired
    ContratRepository contratRepository;

    @Autowired
    EquipeRepository equipeRepository;

    @Autowired
    DepartementRepository departementRepository;

    public List<Etudiant> retrieveAllEtudiants() {//done
        log.info("Retrieving all students");
        List<Etudiant> etudiants = (List<Etudiant>) etudiantRepository.findAll();
        log.info("Number of students retrieved: {}", etudiants.size());
        return etudiants;
    }

    public Etudiant addEtudiant(Etudiant e) {//done
        log.info("Adding student with ID: {}", e.getIdEtudiant());
        Etudiant savedEtudiant = etudiantRepository.save(e);
        log.info("Student added with ID: {}", savedEtudiant.getIdEtudiant());
        return savedEtudiant;
    }

    public Etudiant updateEtudiant(Etudiant e) {//done
        log.info("Updating student with ID: {}", e.getIdEtudiant());
        Etudiant updatedEtudiant = etudiantRepository.save(e);
        log.info("Student updated with ID: {}", updatedEtudiant.getIdEtudiant());
        return updatedEtudiant;
    }

    public Etudiant retrieveEtudiant(Integer idEtudiant) {//done
        log.info("Retrieving student with ID: {}", idEtudiant);
        Etudiant etudiant = etudiantRepository.findById(idEtudiant).orElse(null);
        if (etudiant != null) {
            log.info("Student found with ID: {}", etudiant.getIdEtudiant());
        } else {
            log.warn("No student found with ID: {}", idEtudiant);
        }
        return etudiant;
    }

    public void removeEtudiant(Integer idEtudiant) {//done
        log.info("Removing student with ID: {}", idEtudiant);
        Etudiant e = retrieveEtudiant(idEtudiant);
        if (e != null) {
            etudiantRepository.delete(e);
            log.info("Student removed with ID: {}", e.getIdEtudiant());
        } else {
            log.warn("No student found with ID: {}", idEtudiant);
        }
    }

    public void assignEtudiantToDepartement(Integer etudiantId, Integer departementId) {//done
        log.info("Assigning student ID: {} to department ID: {}", etudiantId, departementId);
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        Departement departement = departementRepository.findById(departementId).orElse(null);
        if (etudiant != null && departement != null) {
            etudiant.setDepartement(departement);
            etudiantRepository.save(etudiant);
            log.info("Student with ID {} assigned to department {}", etudiant.getIdEtudiant(), departement.getNomDepart()); // Use getNomDepart()
        } else {
            log.warn("Failed to assign student: Student or department not found.");
        }
    }

    @Transactional
    public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe) {
        log.info("Adding and assigning student with ID: {} to contract ID: {} and team ID: {}", e.getIdEtudiant(), idContrat, idEquipe);
        Contrat c = contratRepository.findById(idContrat).orElse(null);
        Equipe eq = equipeRepository.findById(idEquipe).orElse(null);
        if (c != null && eq != null) {
            c.setEtudiant(e);
            eq.getEtudiants().add(e);
            etudiantRepository.save(e); // Save student after adding to equipe
            log.info("Student with ID {} added to contract and team", e.getIdEtudiant());
            return e;
        } else {
            log.warn("Failed to add student: Contract or team not found.");
            return null;
        }
    }

    public List<Etudiant> getEtudiantsByDepartement(Integer idDepartement) {//done
        log.info("Retrieving students for department ID: {}", idDepartement);
        List<Etudiant> etudiants = etudiantRepository.findEtudiantsByDepartement_IdDepart(idDepartement);
        log.info("Number of students found in department {}: {}", idDepartement, etudiants.size());
        return etudiants;
    }
}

