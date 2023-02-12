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
		model.addAttribute("esami", professoreService.findById(id).getEsami());
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

	@PostMapping("/professore/add/")
	public String aggiungiProfessore( @Valid@ModelAttribute("professore") Professore professore, BindingResult bindingResult,
			@RequestParam("nomeEsame")String nomeEsame, Model model){

		professoreValidator.validate(professore, bindingResult);

		if(!bindingResult.hasErrors()) {
			if(nomeEsame != null)
				professore.getEsami().add(esameService.findByNomeEsame(nomeEsame));
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

	@GetMapping("/professore/delete/")
	public String deleteAllProfessori(Model model) {
		professoreService.deleteAllProfessori();
		model.addAttribute("singolo", 2); //sto cancellando tutti i professori
		return "professore/professoreCancellazioneOk";
	}

	@GetMapping("/professore/delete/{idProfessore}/{idEsame}")
	public String cancellaEsameDalCorso(@PathVariable("idProfessore") Long idProfessore, @PathVariable("idEsame")Long idEsame, Model model) {
		Professore professore = professoreService.findById(idProfessore);
		esameService.removeElement(professore, esameService.findById(idEsame));
		model.addAttribute("singolo", 3); // sto cancellando un esame dalla lista dei corsi
		return  "corso/corsoCancellazioneOk";
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
		model.addAttribute("esami", esameService.findAll());
		return "professore/professoreEditForm";
	}

	@PostMapping("/professore/edit/{id}")
	public String AggiornaProfessore(@Valid@ModelAttribute("professore") Professore professore,	BindingResult bindingResult, 
			@RequestParam("nomeEsame") String nomeEsame, Model model) {

		if(!bindingResult.hasErrors()) {
			if(nomeEsame != null  && esameService.findAll().contains(esameService.findByNomeEsame(nomeEsame))) {
				professoreService.saveEsame(esameService.findByNomeEsame(nomeEsame), professore.getId());
				model.addAttribute("professore", professore);
				return "professore/professoreEditOk";
			}
		}
		model.addAttribute("professore", professore);
		return "professore/professoreEditErrore";
	}


}
