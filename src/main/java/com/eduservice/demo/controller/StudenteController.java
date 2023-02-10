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
import com.eduservice.demo.model.Studente;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.service.EsameService;
import com.eduservice.demo.service.StudenteService;
import com.eduservice.demo.service.UtenteService;
import com.eduservice.demo.validator.StudenteValidator;

@Controller
public class StudenteController {

	@Autowired
	private StudenteService studenteService;
	
	@Autowired
	private EsameService esameService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private StudenteValidator studenteValidator;
	
	//UTENTE GENERICO

	@GetMapping("/studente/{id}")
	public String getStudente( @PathVariable("id") Long id, Model model) {
		model.addAttribute("studente", studenteService.findById(id));
		return "studente/studente";
	}

	@GetMapping("/studente/")
	public String getAllStudenti( Model model) {
		model.addAttribute("studenti", studenteService.findAll());
		return "studente/studenti";
	}
	
	@GetMapping("/studente/prenota/{idStudente}")
	public String showPrenotazioneEsamiForm(@PathVariable("id") Long idStudente, Model model) {
		model.addAttribute("studente", studenteService.findById(idStudente));
		model.addAttribute("esami", esameService.findAll());
		model.addAttribute("esame", new Esame());
		return "studente/studentePrenotazione";
	}
	
	@PostMapping("/studente/prenota/{idStudente}")
	public String prenotazioneEsami(@PathVariable("id") Long idStudente, Esame esame, Model model) {
		Studente studente = studenteService.findById(idStudente);
		studente.getEsami().add(esame);
		esame.getStudenti().add(studente);
		esameService.updateEsame(esame);
		studenteService.saveStudente(studente);
		return  "studente/studentePrenotazioneOk";
	}

	//AMMINISTRATORE

	@GetMapping("/studente/add/")
	public String showStudenteAddForm(Model model) {
		model.addAttribute("studente", new Studente());
		model.addAttribute("utente", new Utente());
		return "studente/studenteAddForm";
	}

	@PostMapping("/studente/add/{id}")
	public String aggiungiStudente( @Valid@ModelAttribute("studente") Studente studente, BindingResult bindingResult, Model model){
		
		studenteValidator.validate(studente, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			if( !utenteService.existsByMatricola(studente.getMatricolaStudente())) {
				utenteService.saveUtente(studente);
				studenteService.saveStudente(studente);
				return "studente/studente";
			}
		}
		return "studente/studenteAddFormErrore";

	}
	
	//CANCELLAZIONE
	
	@GetMapping("/studente/avviso/delete/{id}")
	public String showAvvisoCancellazioneStudente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("studente", studenteService.findById(id));
		return "studente/studenteAvvisoCancellazioneStudente";
	}

	@GetMapping("/studente/delete/{id}")
	public String deleteStudente(@PathVariable("id") Long id, Model model) {
		studenteService.deleteStudente(id);
		utenteService.deleteUtente(studenteService.findById(id).getMatricolaStudente());
		model.addAttribute("singolo", 1); // sto cancellando un signolo corso
		return "studente/studenteCancellazioneOk";
	}

	@GetMapping("/studente/delete/")
	public String deleteAllStudenti(Model model) {
		studenteService.deleteAllStudenti();
		utenteService.deleteAll();
		model.addAttribute("singolo", 2); //sto cancellando tutti i corsi
		return "studente/studenteCancellazioneOk";
	}

	//il model singolo mi serve per il template di cancellazione
	
	//AGGIORNAMENTO
	
	@GetMapping("/studente/edit/{id}")
	public String showStudenteEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("studente", studenteService.findById(id));
		return "studente/studenteEditForm";
	}

	@PostMapping("/studente/edit/{id}")
	public String AggiornaProfessore(@Valid@ModelAttribute("studente") Studente studente, BindingResult bindingResult, Model model) {

		if(!bindingResult.hasErrors()) {
			studenteService.updateStudente(studente);
			utenteService.updateUtente(studente);
			return "studente/studenteEditOk";
		}
		return "studente/studenteEditErrore";
	}
}
