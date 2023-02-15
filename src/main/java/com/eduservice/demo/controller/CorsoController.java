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
import com.eduservice.demo.model.Esame;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.service.DipartimentoService;
import com.eduservice.demo.service.EsameService;
import com.eduservice.demo.service.ProfessoreService;
import com.eduservice.demo.validator.CorsoValidator;

@Controller
public class CorsoController {

	@Autowired
	CorsoService corsoService;

	@Autowired
	EsameService esameService;

	@Autowired
	ProfessoreService professoreService;

	@Autowired
	DipartimentoService dipartimentoService;

	@Autowired
	CorsoValidator corsoValidator;

	//STUDENTE GENERICO

	@GetMapping("/corso/{id}")
	public String getCorso(@PathVariable("id")Long id, Model model) {
		Corso corso = corsoService.findById(id);
		model.addAttribute("corso", corso);
		model.addAttribute("esami", corso.getEsami());
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
		model.addAttribute("professori", professoreService.findAll());
		model.addAttribute("corso", new Corso());
		return "corso/corsoAddForm";
	}

	@PostMapping("/corso/add/")
	public String AggiungiCorso(@Valid@ModelAttribute("corso") Corso corso, BindingResult bindingResult,
			@RequestParam("nomeEsame") String nomeEsame, @RequestParam("cognomeProfessore") String cognomeProfessore, Model model) {



		corsoValidator.validate(corso, bindingResult);

		if(!bindingResult.hasErrors()) {
			if(nomeEsame !=null) {
				corso.getEsami().add(esameService.findByNomeEsame(nomeEsame));
			}
			if(cognomeProfessore != null) {
				corso.setProfessore(professoreService.findByCognomeProfessore(cognomeProfessore));
			}
			model.addAttribute("corso", corso);
			corsoService.saveCorso(corso);
			return "corso/corso";
		}
		return "corso/corsoAddForm";
	}

	//CANCELLAZIONE

	@GetMapping("/corso/delete/{idCorso}")
	public String deleteCorso(@PathVariable("idCorso") Long id, Model model) {
		corsoService.deleteCorso(id);
		model.addAttribute("singolo", 1); // sto cancellando un signolo corso
		return "corso/corsoCancellazioneOk";
	}

	@GetMapping("/corso/delete/{idCorso}/{idEsame}")
	public String cancellaEsameDalCorso(@PathVariable("idCorso") Long idCorso, @PathVariable("idEsame")Long idEsame, Model model) {
		esameService.emptyCorso(esameService.findById(idEsame));
		model.addAttribute("singolo", 2); // sto cancellando un esame dalla lista dei corsi
		return  "corso/corsoCancellazioneOk";
	}

	@GetMapping("/professore/delete/{idProfessore}/{idCorso}")
	public String cancellaCorsoDalProfessore(@PathVariable("idProfessore") Long idProfessore, @PathVariable("idCorso")Long idCorso, Model model) {
		corsoService.removeProfessore(idCorso);
		model.addAttribute("singolo",3); // sto cancellando un professore dalla lista dei corsi
		return  "corso/corsoCancellazioneOk";
	}

	@GetMapping("/corso/delete/form/")
	public String sowAllCorsiDelete( Model model) {
		model.addAttribute("corsi", corsoService.findAll());
		return "corso/corsiDelete";
	}

	//il model singolo mi serve per il template di cancellazione


	//AGGIORNAMENTO

	@GetMapping("/corso/edit/{id}")
	public String showCorsoEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("corso", corsoService.findById(id));
		model.addAttribute("esami", esameService.findAll());
		model.addAttribute("professori", professoreService.findAll());
		return "corso/corsoEditForm";
	}

	@PostMapping("/corso/edit/{id}")
	public String AggiornaCorso(@Valid@ModelAttribute("corso") Corso corso,	BindingResult bindingResult, 
			@RequestParam("nomeEsame") String nomeEsame, @RequestParam("cognomeProfessore") String cognomeProfessore, Model model) {

		if(!bindingResult.hasErrors()) {
			if(nomeEsame != null && esameService.findAll().contains(esameService.findByNomeEsame(nomeEsame))) {
				Esame esame = esameService.findByNomeEsame(nomeEsame);
				esame.setCorso(corso);
				esameService.saveEsame(esame);
			}
			if(cognomeProfessore != null && professoreService.findAll().contains(professoreService.findByCognomeProfessore(cognomeProfessore)))
				corsoService.saveProfessore(professoreService.findByCognomeProfessore(cognomeProfessore), corso);
			corsoService.updateCorso(corso);
			model.addAttribute("corso", corso);
			return "corso/corsoEditOk";
		}
		model.addAttribute("corso", corso);
		return "corso/corsoEditErrore";
	}
	
	

}
