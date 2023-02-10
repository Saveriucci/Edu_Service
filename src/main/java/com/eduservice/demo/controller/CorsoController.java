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

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Esame;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.service.EsameService;

import com.eduservice.demo.validator.CorsoValidator;

@Controller
public class CorsoController {

	@Autowired
	CorsoService corsoService;

	@Autowired
	EsameService esameService;

	@Autowired
	CorsoValidator corsoValidator;

	//STUDENTE GENERICO

	@GetMapping("/corso/{id}")
	public String getCorso(@PathVariable("id")Long id, Model model) {
		Corso corso = corsoService.findById(id);
		model.addAttribute("corso", corso);
		return "corso/corso";
	}

	@GetMapping("/corso/")
	public String getAllCorsi( Model model) {
		model.addAttribute("corsi", corsoService.findAll());
		return "corso/corsi";
	}

	//AMMINISTRATORE

	//INSERIMENTO
	@GetMapping("/corso/add/")
	public String showFormInserimentoCorso( Model model) {
		model.addAttribute("esami", esameService.findAll());
		model.addAttribute("corso", new Corso());
		return "corso/corsoAddForm";
	}

	@PostMapping("/corso/add/")
	public String AggiungiCorso(@Valid@ModelAttribute("corso") Corso corso, BindingResult bindingResult,
			Long idEsame, Model model) {
		


		corsoValidator.validate(corso, bindingResult);
		

		if(!bindingResult.hasErrors()) {
			if(idEsame !=null) {
				corso.getEsami().add(esameService.findById(idEsame));
			}
			model.addAttribute("corso", corso);
			corsoService.saveCorso(corso);
			return "corso/corso";
		}
		return "corso/corsoAddFormErrore";
	}

	//CANCELLAZIONE

	@GetMapping("/corso/delete/{id}")
	public String deleteCorso(@PathVariable("id") Long id, Model model) {
		corsoService.deleteCorso(id);
		model.addAttribute("singolo", 1); // sto cancellando un signolo corso
		return "corso/corsoCancellazioneOk";
	}

	@GetMapping("/corso/delete/")
	public String deleteAllCorsi(Model model) {
		corsoService.deleteAllCorsi();
		model.addAttribute("singolo", 2); //sto cancellando tutti i corsi
		return "corso/corsoCancellazioneOk";
	}

	//il model singolo mi serve per il template di cancellazione

	//AGGIORNAMENTO

	@GetMapping("/corso/edit/{id}")
	public String showCorsoEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("corso", corsoService.findById(id));
		return "corso/corsoEditForm";
	}

	@PostMapping("/corso/edit/{id}")
	public String AggiornaCorso(@Valid@ModelAttribute("corso") Corso corso,	BindingResult bindingResult, Model model) {

		
		if(!bindingResult.hasErrors()) {
			corsoService.updateCorso(corso);
			return "corso/corsoEditOk";
		}
		return "corso/corsoEditErrore";
	}

	@GetMapping("/corso/edit/aggiungiEsami/{idCorso}")
	public String showCorsoAggiungiEsami(@PathVariable("idCorso") Long id, Model model) {
		model.addAttribute("corso", corsoService.findById(id));
		model.addAttribute("esami", esameService.findAll());
		return "corso/corsoAggiungiEsami";
	}

	@PostMapping("/corso/edit/aggiungiEsami/{idCorso}")
	public String AggiungiEsameAlCorso(@PathVariable("idCorso") Long id, Esame esame,  Model model) {
		corsoService.saveEsame(esame, id);
		return "corso/corsoAggiungiEsamiOk";
	}

}
