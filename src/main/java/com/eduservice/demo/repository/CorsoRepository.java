package com.eduservice.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Professore;

public interface CorsoRepository  extends CrudRepository<Corso, Long>{

	public boolean existsByNomeCorso( String nomeCorso);

	public Corso findByNomeCorso(String nomeCorso);

	public List<Corso> findCorsoByProfessore(Professore professore);
	
}
