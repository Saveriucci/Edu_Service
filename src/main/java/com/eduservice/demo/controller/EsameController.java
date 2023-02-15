package com.eduservice.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eduservice.demo.model.Esame;
import com.eduservice.demo.model.Studente;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.service.EsameService;
import com.eduservice.demo.service.StudenteService;
import com.eduservice.demo.validator.EsameValidator;

@Controller
public class EsameController {

	@Autowired
	private EsameService esameService;

	@Autowired
	private CorsoService corsoService;

	@Autowired
	private StudenteService studenteService;

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
		//richiedo  l username dopo il login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//getname() = username (stringa)
		//integer.valueOf = numero trasformato da una stringa
		Studente studente = studenteService.findByMatricola(Integer.valueOf(auth.getName()));
		model.addAttribute("esamiStudente", studenteService.getEsamiStudenteByIdStudente(studente.getId()));
		model.addAttribute("studente", studente);
		model.addAttribute("esami", esameService.findAll());
		return "esame/esami";
	}

	//AMMINISTRATORE

	@GetMapping("/esame/add/")
	public String showEsameAddForm(Model model) {
		model.addAttribute("esame", new Esame());
		model.addAttribute("corsi", corsoService.findAll());
		return "esame/esameAddForm";
	}

	@PostMapping("/esame/add/")
	public String aggiungiEsame( @Valid@ModelAttribute("esame") Esame esame, BindingResult bindingResult,
			@RequestParam("nomeCorso") String nomeCorso, Model model){


		esameValidator.validate(esame, bindingResult);

		if(!bindingResult.hasErrors()) {
			if( nomeCorso != null)
				esame.setCorso(corsoService.findBynomeCorso(nomeCorso));
			esameService.saveEsame(esame);
			return "esame/esame";
		}
		return "esame/esameAddForm";

	}

	//CANCELLAZIONE

	@GetMapping("/esame/delete/{id}")
	public String deleteEsame(@PathVariable("id") Long id, Model model) {
		esameService.findById(id).getStudenti().clear();
		esameService.deleteEsame(id);
		model.addAttribute("singolo");
		return "esame/esameCancellazioneOk";
	}

	//AGGIORNAMENTO

	@GetMapping("/esame/edit/{id}")
	public String showEsameEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("esame", esameService.findById(id));
		model.addAttribute("corsi", corsoService.findAll());
		return "esame/esameEditForm";
	}

	@PostMapping("esame/edit/{id}")
	public String AggiornaEsami(@Valid@ModelAttribute("esame") Esame esame,BindingResult bindingResult, Model model) {

		if(!bindingResult.hasErrors()) {
			esameService.updateEsame(esame);
			model.addAttribute("esame", esame);
			return "esame/esameEditOk";
		}

		model.addAttribute("esame", esame);
		return "esame/esameEditErrore";
	}


}
