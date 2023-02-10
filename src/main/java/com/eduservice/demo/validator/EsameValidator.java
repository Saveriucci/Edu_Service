package com.eduservice.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eduservice.demo.model.Esame;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.service.EsameService;

@Component
public class EsameValidator implements Validator {
	
	@Autowired EsameService esameService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Esame esame = (Esame) target;
		
		if( esameService.existsByNomeEsameAndTipoSessioneAndDataEsame(esame.getNomeEsame(), 
				esame.getTipoSessione(), esame.getDataEsame())) {
			errors.reject("esame.duplicato");
		}
	}

}
