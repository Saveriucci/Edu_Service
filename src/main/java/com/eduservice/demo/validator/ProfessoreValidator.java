package com.eduservice.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eduservice.demo.model.Professore;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.service.ProfessoreService;

@Component
public class ProfessoreValidator implements Validator {

	@Autowired ProfessoreService professoreService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Professore professore = (Professore) target;
		
		if( professoreService.existsByNomeProfessoreAndCognomeProfessore(professore.getNomeProfessore(), professore.getCognomeProfessore())) {
			errors.reject("professore.duplicato");
		}
	}
}
