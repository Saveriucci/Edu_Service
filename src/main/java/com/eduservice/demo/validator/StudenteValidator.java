package com.eduservice.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eduservice.demo.model.Studente;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.service.StudenteService;

@Component
public class StudenteValidator implements Validator {

	@Autowired StudenteService studenteService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Studente studente = (Studente) target;
		
		if( studenteService.existsByMatricola(studente.getMatricolaStudente())) {
			errors.reject("studente.duplicato");
		}
	}
}
