package com.eduservice.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Dipartimento;

public interface DipartimentoRepository extends CrudRepository<Dipartimento, Long>{

	public boolean existsByNomeDipartimento( String nomeDipartimento);
}
