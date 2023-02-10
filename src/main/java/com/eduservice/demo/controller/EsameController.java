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
import com.eduservice.demo.model.Professore;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.service.EsameService;
import com.eduservice.demo.service.ProfessoreService;
import com.eduservice.demo.validator.EsameValidator;

@Controller
public class EsameController {
	
	@Autowired
	private EsameService esameService;
	
	@Autowired
	private CorsoService corsoService;
	
	@Autowired
	private ProfessoreService professoreService;

	@Autowired
	private EsameValidator esameValidator;
	
	//UTENTE GENERICO

	@GetMapping("/esame/{id}")
	public String getEsame( @PathVariable("id") Long id, Model model) {
		model.addAttribute("esame", esameService.findById(id));
		return "esame/esame";
	}

	@GetMapping("/esame/")
	public String getAllEsami( Model model) {
		model.addAttribute("esami", esameService.findAll());
		return "esame/esami";
	}

	//AMMINISTRATORE

	@GetMapping("/esame/add/")
	public String showEsameAddForm(Model model) {
		model.addAttribute("esame", new Esame());
		model.addAttribute("corsi", corsoService.findAll());
		model.addAttribute("professori", professoreService.findAll());
		return "esame/esameAddForm";
	}

	@PostMapping("/esame/add/{id}")
	public String aggiungiEsame( @Valid@ModelAttribute("esame") Esame esame, BindingResult bindingResult,
			Long idProfessore, Long idCorso, Model model){
		
		esameValidator.validate(esame, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			esame.setCorso(corsoService.findById(idCorso));
			esame.setProfessore(professoreService.findById(idProfessore));
			esameService.saveEsame(esame);
			return "esame/esame";
		}
		return "esame/esameAddFormErrore";

	}
	
	//CANCELLAZIONE

	@GetMapping("/esame/delete/{id}")
	public String deleteEsame(@PathVariable("id") Long id, Model model) {
		esameService.deleteEsame(id);
		model.addAttribute("singolo", 1); // sto cancellando un signolo corso
		return "esame/esameCancellazioneOk";
	}

	@GetMapping("/esame/delete/")
	public String deleteAllEsami(Model model) {
		esameService.deleteAllEsami();
		model.addAttribute("singolo", 2); //sto cancellando tutti i corsi
		return "esame/esameCancellazioneOk";
	}

	//il model singolo mi serve per il template di cancellazione
	
	//AGGIORNAMENTO
	
	@GetMapping("/esame/edit/{id}")
	public String showEsameEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("esame", esameService.findById(id));
		return "esame/esameEditForm";
	}

	@PostMapping("esame/edit/{id}")
	public String AggiornaEsami(@Valid@ModelAttribute("esame") Esame esame,
			BindingResult bindingResult, Model model) {

		if(!bindingResult.hasErrors()) {
			esameService.updateEsame(esame);
			return "esame/esameEditOk";
		}
		return "esame/esameEditErrore";
	}

	@GetMapping("/esame/edit/aggiungi/{idEsame}")
	public String showProfessoreAggiungiEsame(@PathVariable("idEsame") Long id, Model model) {
		model.addAttribute("esame", esameService.findById(id));
		model.addAttribute("corsi", corsoService.findAll());
		model.addAttribute("professori", professoreService.findAll());
		return "esame/esameAggiungiForm";
	}

	@PostMapping("/esame/edit/aggiungi/{idEsame}")
	public String AggiungiCorsoAlDipartimento(@PathVariable("idEsame") Long id, Professore professore, 
			Corso corso, Model model) {
		esameService.saveCorso(corso, id);
		esameService.saveProfessore(professore, id);
		return "esame/esameAggiungiOk";
	}

}
