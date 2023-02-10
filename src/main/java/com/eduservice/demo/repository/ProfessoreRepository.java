package com.eduservice.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Professore;

public interface ProfessoreRepository extends CrudRepository<Professore, Long>{

	public boolean existsByNomeProfessoreAndCognomeProfessore( String nomeProfessore, String cognomeProfessore);
	
	public Professore findByNomeProfessoreAndCognomeProfessore( String nomeProfessore, String cognomeProfessore);
}
