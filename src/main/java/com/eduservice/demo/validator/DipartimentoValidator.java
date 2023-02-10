package com.eduservice.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eduservice.demo.model.Dipartimento;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.service.DipartimentoService;

@Component
public class DipartimentoValidator implements Validator{

	@Autowired DipartimentoService dipartimentoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Dipartimento dipartimento = (Dipartimento) target;
		
		if( dipartimentoService.existsByNomeDipartimento(dipartimento.getNomeDipartimento())) {
			errors.reject("dipartimento.duplicato");
		}
	}
}
