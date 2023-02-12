package com.eduservice.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Professore;

public interface ProfessoreRepository extends CrudRepository<Professore, Long>{

	public Professore findByCognomeProfessore(String cognomeProfessore);

	public boolean existsByCognomeProfessore(String cognomeProfessore);
}
