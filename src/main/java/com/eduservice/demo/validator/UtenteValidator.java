package com.eduservice.demo.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eduservice.demo.model.Utente;
import com.eduservice.demo.service.UtenteService;


@Component
public class UtenteValidator implements Validator{

	@Autowired private UtenteService utenteService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Utente utente = (Utente) target;
		if(utenteService.existsByUsername(utente)) {
			errors.reject("utente.duplicato");
		}
		
	}


}
