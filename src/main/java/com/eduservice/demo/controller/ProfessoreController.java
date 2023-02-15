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
import org.springframework.web.bind.annotation.RequestParam;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Professore;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.service.ProfessoreService;
import com.eduservice.demo.validator.ProfessoreValidator;

@Controller
public class ProfessoreController {

	@Autowired
	private ProfessoreService professoreService;

	@Autowired
	private CorsoService corsoService;

	@Autowired
	private ProfessoreValidator professoreValidator;


	//UTENTE GENERICO

	@GetMapping("/professore/{id}")
	public String getProfessore( @PathVariable("id") Long id, Model model) {
		Professore professore = professoreService.findById(id);
		model.addAttribute("professore", professore);
		model.addAttribute("esami", corsoService.findEsamiByCognomeProfessore(professore));
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
		model.addAttribute("corsi", corsoService.findAll());
		return "professore/professoreAddForm";
	}

	@PostMapping("/professore/add/")
	public String aggiungiProfessore( @Valid@ModelAttribute("professore") Professore professore, BindingResult bindingResult,
			@RequestParam("nomeCorso")String nomeCorso, Model model){

		professoreValidator.validate(professore, bindingResult);

		if(!bindingResult.hasErrors()) {
			if(nomeCorso != null)
				professore.getCorsi().add(corsoService.findByNomeCorso(nomeCorso));
			professoreService.SaveProfessore(professore);
			return "professore/professore";
		}
		return "professore/professoreAddForm";

	}

	//CANCELLAZIONE

	@GetMapping("/professore/delete/{id}")
	public String deleteProfessore(@PathVariable("id") Long id, Model model) {
		professoreService.deleteProfessore(id);
		model.addAttribute("singolo", 1); // sto cancellando un signolo professore
		return "professore/professoreCancellazioneOk";
	}
	

	@GetMapping("/professore/delete/form/")
	public String showAllProfessoriDelete( Model model) {
		model.addAttribute("professori", professoreService.findAll());
		return "professore/professoriDelete";
	}

	//il model singolo mi serve per il template di cancellazione

	//AGGIORNAMENTO

	@GetMapping("/professore/edit/{id}")
	public String showProfessoreEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("professore", professoreService.findById(id));
		model.addAttribute("corsi", corsoService.findAll());
		return "professore/professoreEditForm";
	}

	@PostMapping("/professore/edit/{id}")
	public String AggiornaProfessore(@Valid@ModelAttribute("professore") Professore professore,	BindingResult bindingResult, 
			@RequestParam("nomeCorso") String nomeCorso, Model model) {

		if(!bindingResult.hasErrors()) {
			if(nomeCorso != null  && corsoService.findAll().contains(corsoService.findByNomeCorso(nomeCorso))) {
				Corso corso = corsoService.findBynomeCorso(nomeCorso);
				corsoService.saveProfessore(professore, corso);
			}
			professoreService.updateProfessore(professore);
			model.addAttribute("professore", professore);
			return "professore/professoreEditOk";
		}
		model.addAttribute("professore", professore);
		return "professore/professoreEditErrore";
	}


}
