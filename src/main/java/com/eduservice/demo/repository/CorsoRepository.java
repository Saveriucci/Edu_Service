package com.eduservice.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Corso;

public interface CorsoRepository  extends CrudRepository<Corso, Long>{

	public boolean existsByNomeCorso( String nomeCorso);
}
