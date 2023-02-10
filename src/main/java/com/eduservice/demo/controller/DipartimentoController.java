package com.eduservice.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Dipartimento;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.service.DipartimentoService;
import com.eduservice.demo.service.UtenteService;
import com.eduservice.demo.validator.DipartimentoValidator;

@Controller
public class DipartimentoController {

	@Autowired
	private DipartimentoService dipartimentoService;

	@Autowired
	private CorsoService corsoService;
	
	@Autowired
	private UtenteService utenteService;

	@Autowired
	private DipartimentoValidator dipartimentoValidator;


	//UTENTE GENERICO

	@GetMapping("/dipartimento/{id}")
	public String getDipartimento( @PathVariable("id") Long id, Model model) {
		model.addAttribute("dipartimento", dipartimentoService.findById(id));
		model.addAttribute("user", utenteService.findByUsername(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())));
		return "dipartimento/dipartimento";
	}

	@GetMapping("/dipartimento/")
	public String getAllDipartimenti( Model model) {
		model.addAttribute("dipartimenti", dipartimentoService.findAll());
		model.addAttribute("user", utenteService.findByUsername(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())));
		return "dipartimento/dipartimenti";
	}

	//AMMINISTRATORE

	@GetMapping("/dipartimento/add/")
	public String showDipartimentoAddForm(Model model) {
		model.addAttribute("dipartimento", new Dipartimento());
		model.addAttribute("corsi", corsoService.findAll());
		model.addAttribute("user", utenteService.findByUsername(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())));
		return "dipartimento/dipartimentoAddForm";
	}

	@PostMapping("/dipartimento/add/")
	public String aggiungiDipartimento( @Valid@ModelAttribute("dipartimento") Dipartimento dipartimento, BindingResult bindingResult,
			Long idCorso, Model model){
		
		dipartimentoValidator.validate(dipartimento, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			//dipartimento.getCorsi().add(corsoService.findById(idCorso));
			dipartimentoService.saveDipartimento(dipartimento);
			return "dipartimento/dipartimento";
		}
		return "dipartimento/dipartimentoAddFormErrore";

	}
	
	//CANCELLAZIONE

	@GetMapping("/dipartimento/delete/{id}")
	public String deleteCorso(@PathVariable("id") Long id, Model model) {
		dipartimentoService.deleteDipartimento(id);
		model.addAttribute("singolo", 1); // sto cancellando un signolo corso
		return "dipartimento/dipartimentoCancellazioneOk";
	}

	@GetMapping("/dipartimento/delete/")
	public String deleteAllCorsi(Model model) {
		dipartimentoService.deleteAllDipartimenti();
		model.addAttribute("singolo", 2); //sto cancellando tutti i corsi
		return "dipartimento/dipartimentoCancellazioneOk";
	}

	//il model singolo mi serve per il template di cancellazione
	
	//AGGIORNAMENTO
	
	@GetMapping("/dipartimento/edit/{id}")
	public String showDipartimentoEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("dipartimento", dipartimentoService.findById(id));
		return "dipartimento/dipartimentoEditForm";
	}

	@PostMapping("/dipartimento/edit/{id}")
	public String AggiornaDipartimento(@Valid@ModelAttribute("dipartimento") Dipartimento dipartimento,
			BindingResult bindingResult, Model model) {

		if(!bindingResult.hasErrors()) {
			dipartimentoService.updateDipartimento(dipartimento);
			return "dipartimento/dipartimentoEditOk";
		}
		return "dipartimento/dipartimentoEditErrore";
	}

	@GetMapping("/dipartimento/edit/aggiungiCorso/{idDipartimento}")
	public String showDipartimentoAggiungiCorsi(@PathVariable("idDipartimento") Long id, Model model) {
		model.addAttribute("dipartimento", dipartimentoService.findById(id));
		model.addAttribute("corsi", corsoService.findAll());
		return "dipartimento/dipartimentoAggiungiCorso";
	}

	@PostMapping("/dipartimento/edit/aggiungiCorso/{idDipartimento}")
	public String AggiungiCorsoAlDipartimento(@PathVariable("idDipartimento") Long id, Corso corso,  Model model) {
		dipartimentoService.saveCorso(corso, id);
		return "dipartimento/dipartimentoAggiungiCorsoOk";
	}


}
