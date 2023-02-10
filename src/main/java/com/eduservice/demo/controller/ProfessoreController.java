package com.eduservice.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eduservice.demo.model.Esame;
import com.eduservice.demo.model.Professore;
import com.eduservice.demo.service.EsameService;
import com.eduservice.demo.service.ProfessoreService;
import com.eduservice.demo.validator.ProfessoreValidator;

@Controller
public class ProfessoreController {

	@Autowired
	private ProfessoreService professoreService;
	
	@Autowired
	private EsameService esameService;
	
	@Autowired
	private ProfessoreValidator professoreValidator;
	
	
	//UTENTE GENERICO

	@GetMapping("/professore/{id}")
	public String getProfessore( @PathVariable("id") Long id, Model model) {
		model.addAttribute("professore", professoreService.findById(id));
		return "professore/professore";
	}

	@GetMapping("/professore/")
	public String getAllProfessori( Model model) {
		model.addAttribute("professori", professoreService.findAll());
		return "professore/professori";
	}

	//AMMINISTRATORE

	@GetMapping("/professore/add/")
	public String showProfessoreAddForm(Model model) {
		model.addAttribute("professore", new Professore());
		model.addAttribute("esami", esameService.findAll());
		return "professore/professoreAddForm";
	}

	@PostMapping("/professore/add/{id}")
	public String aggiungiProfessore( @Valid@ModelAttribute("professore") Professore professore, BindingResult bindingResult,
			Long idEsame, Model model){
		
		professoreValidator.validate(professore, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			professore.getEsami().add(esameService.findById(idEsame));
			professoreService.SaveProfessore(professore);
			return "professore/professore";
		}
		return "professore/professoreAddFormErrore";

	}
	
	//CANCELLAZIONE

	@GetMapping("/professore/delete/{id}")
	public String deleteProfessore(@PathVariable("id") Long id, Model model) {
		professoreService.deleteProfessore(id);
		model.addAttribute("singolo", 1); // sto cancellando un signolo corso
		return "professore/professoreCancellazioneOk";
	}

	@GetMapping("/professore/delete/")
	public String deleteAllProfessori(Model model) {
		professoreService.deleteAllProfessori();
		model.addAttribute("singolo", 2); //sto cancellando tutti i corsi
		return "professore/professoreCancellazioneOk";
	}

	//il model singolo mi serve per il template di cancellazione
	
	//AGGIORNAMENTO
	
	@GetMapping("/professore/edit/{id}")
	public String showProfessoreEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("professore", professoreService.findById(id));
		return "professore/professoreEditForm";
	}

	@PostMapping("/professore/edit/{id}")
	public String AggiornaProfessore(@Valid@ModelAttribute("professore") Professore professore,
			BindingResult bindingResult, Model model) {

		if(!bindingResult.hasErrors()) {
			professoreService.updateProfessore(professore);
			return "professore/professoreEditOk";
		}
		return "professore/professoreEditErrore";
	}

	@GetMapping("/professore/edit/aggiungiEsame/{idProfessore}")
	public String showProfessoreAggiungiEsame(@PathVariable("idProfessore") Long id, Model model) {
		model.addAttribute("professore", professoreService.findById(id));
		model.addAttribute("esami", esameService.findAll());
		return "professore/professoreAggiungiEsame";
	}

	@PostMapping("/professore/edit/aggiungiEsami/{idProfessore}")
	public String AggiungiCorsoAlDipartimento(@PathVariable("idProfessore") Long id, Esame esame,  Model model) {
		professoreService.saveEsame(esame, id);
		return "professore/professoreAggiungiEsameOk";
	}


}
