package com.eduservice.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Studente;

public interface StudenteRepository extends CrudRepository <Studente, Long>{

	public boolean existsByMatricolaStudente(int matricola);
	
	public Studente findByMatricolaStudente( int matricola);
	
	public List<Studente> findByNomeStudente( String nomeStudente);
	
	public List<Studente> findByCognomeStudente( String cognomeStudente);
	
}
