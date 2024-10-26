package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Import Slf4j for logging

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.services.IEquipeService;

import java.util.List;


@Slf4j // Add this annotation to enable logging
@RestController
@AllArgsConstructor
@RequestMapping("/equipe")
public class EquipeRestController {
	IEquipeService equipeService;
	// http://localhost:8089/Kaddem/equipe/retrieve-all-equipes
	@GetMapping("/retrieve-all-equipes")
	public List<Equipe> getEquipes() {
		log.info("Received request to retrieve all equipes");
		List<Equipe> listEquipes = equipeService.retrieveAllEquipes();
		log.info("Returning {} equipes", listEquipes.size());

		return listEquipes;
	}
	// http://localhost:8089/Kaddem/equipe/retrieve-equipe/8
	@GetMapping("/retrieve-equipe/{equipe-id}")
	public Equipe retrieveEquipe(@PathVariable("equipe-id") Integer equipeId) {
		log.info("Received request to retrieve equipe with id: {}", equipeId);
		return equipeService.retrieveEquipe(equipeId);
	}

	// http://localhost:8089/Kaddem/equipe/add-equipe
	@PostMapping("/add-equipe")
	public Equipe addEquipe(@RequestBody Equipe e) {
		log.info("Received request to add equipe: {}", e.getNomEquipe());

		Equipe equipe = equipeService.addEquipe(e);
		return equipe;
	}

	// http://localhost:8089/Kaddem/equipe/remove-equipe/1
	@DeleteMapping("/remove-equipe/{equipe-id}")
	public void removeEquipe(@PathVariable("equipe-id") Integer equipeId) {
		log.info("Received request to remove equipe with id: {}", equipeId);
		equipeService.deleteEquipe(equipeId);
	}

	// http://localhost:8089/Kaddem/equipe/update-equipe
	@PutMapping("/update-equipe")
	public Equipe updateEtudiant(@RequestBody Equipe e) {
		log.info("Received request to update equipe: {}", e.getNomEquipe());
		Equipe equipe= equipeService.updateEquipe(e);
		return equipe;
	}

	@Scheduled(cron="0 0 13 * * *")
	@PutMapping("/faireEvoluerEquipes")
	public void faireEvoluerEquipes() {
		log.info("Scheduled task to evolve equipes initiated");
		equipeService.evoluerEquipes() ;
	}
}


