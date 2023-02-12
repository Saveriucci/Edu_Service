package com.eduservice.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eduservice.demo.model.Esame;

public interface EsameRepository extends CrudRepository<Esame, Long>{

	public boolean existsByNomeEsameAndTipoSessioneAndDataEsame( String nomeEsame, String tipoSessione, Date dataEsame);
	
	public Esame findByNomeEsame(String nomeEsame);
	
	public List<Esame> findByTipoSessione( String tipoSessione);
	
	public List<Esame> findByDataEsame( Date dataEsame);

	@Query("SELECT  distinct(e.id) FROM Corso c JOIN Esame e on c.id = e WHERE c =?1")
	public Long[] trovaEsamiConIdCorso(Long idCorso);
}
