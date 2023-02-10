package com.eduservice.demo.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.service.CorsoService;
import com.eduservice.demo.model.Utente;


@Component
public class CorsoValidator implements Validator {

	@Autowired CorsoService corsoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Corso corso = (Corso) target;
		
		
		if( corsoService.existsByNomeCorso(corso.getNomeCorso())) {
			errors.reject("corso.duplicato");
		}
	}
	
}
