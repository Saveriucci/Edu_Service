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
import org.springframework.web.bind.annotation.PostMapping;

import com.eduservice.demo.model.Studente;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.service.EsameService;
import com.eduservice.demo.service.StudenteService;
import com.eduservice.demo.service.UtenteService;
import com.eduservice.demo.validator.UtenteValidator;




@Controller
public class UtenteController {

	@Autowired 
	private UtenteService utenteService;

	@Autowired
	private StudenteService studenteService;

	@Autowired
	private EsameService esameService;

	@Autowired
	private UtenteValidator utenteValidator;


	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new Utente());
		return "auth/registrationForm";
	}


	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") Utente user,
			BindingResult userBindingResult, Model model) {

		// validazione user
		utenteValidator.validate(user, userBindingResult);
		if(!userBindingResult.hasErrors()) {
			model.addAttribute("user", user);
			utenteService.saveUtente(user);
			studenteService.saveStudente(user);
			return "auth/loginForm";
		}
		return "auth/registrationForm";
	}


	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "auth/loginForm";
	}


	@GetMapping("/default")
	public String defaultAfterLogin( Model model) {

		//richiedo  l username dopo il login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Utente utente = utenteService.findByUsername(Integer.valueOf(auth.getName()));
		Studente studente = studenteService.findByMatricola(utente.getUsername());


		//getname() = username (stringa)
		//integer.valueOf = numero trasformato da una stringa
		model.addAttribute("user", utente);
		model.addAttribute("studente", studente);
		if(studente != null)
			model.addAttribute("esami", esameService.findEsamiByIdStudente(studente.getId()));
		return "index";

	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return "redirect:/";
	}

	@GetMapping("/")
	public String homepage(Model model) {
		return "auth/loginForm";
	}
}
