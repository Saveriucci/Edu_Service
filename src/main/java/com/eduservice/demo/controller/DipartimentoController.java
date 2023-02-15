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

import com.eduservice.demo.model.Dipartimento;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.service.DipartimentoService;
import com.eduservice.demo.validator.DipartimentoValidator;

@Controller
public class DipartimentoController {

	@Autowired
	private DipartimentoService dipartimentoService;

	@Autowired
	private CorsoService corsoService;

	@Autowired
	private DipartimentoValidator dipartimentoValidator;


	//UTENTE GENERICO

	@GetMapping("/dipartimento/{id}")
	public String getDipartimento( @PathVariable("id") Long id, Model model) {
		model.addAttribute("dipartimento", dipartimentoService.findById(id));
		model.addAttribute("corsi", dipartimentoService.findById(id).getCorsi());
		return "dipartimento/dipartimento";
	}

	@GetMapping("/dipartimento/")
	public String getAllDipartimenti( Model model) {
		model.addAttribute("dipartimenti", dipartimentoService.findAll());
		return "dipartimento/dipartimenti";
	}

	//AMMINISTRATORE

	@GetMapping("/dipartimento/add/")
	public String showDipartimentoAddForm(Model model) {
		model.addAttribute("dipartimento", new Dipartimento());
		model.addAttribute("corsi", corsoService.findAll());
		return "dipartimento/dipartimentoAddForm";
	}

	@PostMapping("/dipartimento/add/")
	public String aggiungiDipartimento( @Valid@ModelAttribute("dipartimento") Dipartimento dipartimento, BindingResult bindingResult,
			@RequestParam("nomeCorso") String nomeCorso, Model model){

		dipartimentoValidator.validate(dipartimento, bindingResult);

		if(!bindingResult.hasErrors()) {
			if(nomeCorso!= null)
				dipartimento.getCorsi().add(corsoService.findBynomeCorso(nomeCorso));
			dipartimentoService.saveDipartimento(dipartimento);
			return "dipartimento/dipartimento";
		}
		return "dipartimento/dipartimentoAddForm";

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

	@GetMapping("/dipartimento/delete/form/")
	public String showAllDipartimentiDelete( Model model) {
		model.addAttribute("dipartimenti", dipartimentoService.findAll());
		return "dipartimento/dipartimentiDelete";
	}

	//il model singolo mi serve per il template di cancellazione

	//AGGIORNAMENTO

	@GetMapping("/dipartimento/edit/{id}")
	public String showDipartimentoEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("dipartimento", dipartimentoService.findById(id));
		model.addAttribute("corsi", corsoService.findAll());
		return "dipartimento/dipartimentoEditForm";
	}

	@PostMapping("/dipartimento/edit/{id}")
	public String AggiornaDipartimento(@Valid@ModelAttribute("dipartimento") Dipartimento dipartimento, BindingResult bindingResult, 
			@RequestParam("nomeCorso") String nomeCorso, Model model) {

		if(dipartimento.getCorsi().contains(corsoService.findBynomeCorso(nomeCorso)))
			return "dipartimento/dipartimentoEditFormErrore";

		if(!bindingResult.hasErrors()) {
			if(nomeCorso != null && corsoService.findAll().contains(corsoService.findBynomeCorso(nomeCorso)))
				dipartimentoService.saveCorso(corsoService.findBynomeCorso(nomeCorso), dipartimento.getId());

			dipartimentoService.updateDipartimento(dipartimento);
			model.addAttribute("dipartimento", dipartimento);
			return "dipartimento/dipartimentoEditOk";
		}
		model.addAttribute("dipartimento", dipartimento);
		return "dipartimento/dipartimentoEditFormErrore";
	}


}
