package com.eduservice.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long>{
	
	public Utente findByUsername(int username);
	
	public boolean existsByUsername( int username);

	public List<Utente> findByRole(String defaultRole);

}
