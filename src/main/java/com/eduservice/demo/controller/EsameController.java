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
import com.eduservice.demo.service.ProfessoreService;
import com.eduservice.demo.service.StudenteService;
import com.eduservice.demo.service.UtenteService;
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
	private StudenteService studenteService;

	@Autowired
	private UtenteService utenteService;

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

		if(utenteService.findByUsername(Integer.valueOf(auth.getName())).getRole() == "generic") {
			//getname() = username (stringa)
			//integer.valueOf = numero trasformato da una stringa
			Studente studente = studenteService.findByMatricola(Integer.valueOf(auth.getName()));
			model.addAttribute("esamiStudente", esameService.findEsamiByIdStudente(studente.getId()));
		}
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

	@PostMapping("/esame/add/")
	public String aggiungiEsame( @Valid@ModelAttribute("esame") Esame esame, BindingResult bindingResult,
			@RequestParam("cognomeProfessore") String cognomeProfessore, @RequestParam("nomeCorso") String nomeCorso, Model model){


		esameValidator.validate(esame, bindingResult);

		if(!bindingResult.hasErrors()) {
			if( nomeCorso != null)
				esame.setCorso(corsoService.findBynomeCorso(nomeCorso));
			if(cognomeProfessore != null)
				esame.setProfessore(professoreService.findByCognomeProfessore(cognomeProfessore));
			esameService.saveEsame(esame);
			return "esame/esame";
		}
		return "esame/esameAddForm";

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

	@GetMapping("/esame/delete/form/")
	public String showAllEsamiDelete( Model model) {
		model.addAttribute("esami", esameService.findAll());
		return "esame/esamiDelete";
	}

	//il model singolo mi serve per il template di cancellazione

	//AGGIORNAMENTO

	@GetMapping("/esame/edit/{id}")
	public String showEsameEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("esame", esameService.findById(id));
		model.addAttribute("corsi", corsoService.findAll());
		model.addAttribute("professori", professoreService.findAll());
		return "esame/esameEditForm";
	}

	@PostMapping("esame/edit/{id}")
	public String AggiornaEsami(@Valid@ModelAttribute("esame") Esame esame,BindingResult bindingResult,
			@RequestParam("nomeCorso") String nomeCorso, @RequestParam("cognomeProfessore") String cognomeProfessore, Model model) {

		if(!bindingResult.hasErrors()) {
			if( nomeCorso != null && cognomeProfessore != null && corsoService.findAll().contains(corsoService.findBynomeCorso(nomeCorso)) 
					&& professoreService.findAll().contains(professoreService.findByCognomeProfessore(cognomeProfessore))) {
				esame.setCorso(corsoService.findBynomeCorso(nomeCorso));
				esame.setProfessore(professoreService.findByCognomeProfessore(cognomeProfessore));
				esameService.updateEsame(esame);
				model.addAttribute("esame", esame);
				return "esame/esameEditOk";
			}
		}
		model.addAttribute("esame", esame);
		return "esame/esameEditErrore";
	}


}
