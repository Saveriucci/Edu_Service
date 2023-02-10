package com.eduservice.demo.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Esame;
import com.eduservice.demo.model.Professore;
import com.eduservice.demo.model.Studente;
import com.eduservice.demo.repository.EsameRepository;

@Service
public class EsameService {

	@Autowired
	EsameRepository esameRepository;

	@Transactional
	public void saveEsame(Esame esame) {
		esameRepository.save(esame);
	}

	@Transactional
	public void deleteEsame( Long id) {
		Esame esame = esameRepository.findById(id).get();
		esameRepository.delete(esame);
	}

	@Transactional
	public void deleteAllEsami() {
		esameRepository.deleteAll();
	}

	@Transactional
	public void updateEsame( Esame esame) {
		Esame esameUpdate = esameRepository.findById(esame.getId()).get();
		esameUpdate.setNomeEsame(esame.getNomeEsame());
		esameUpdate.setDescrizioneEsame(esame.getDescrizioneEsame());
		esameUpdate.setDataEsame(esame.getDataEsame());
		esameUpdate.setTipoSessione(esame.getTipoSessione());
		esameRepository.save(esameUpdate);
	}
	
	public Esame findByNomeEsame( String nomeEsame) {
		return esameRepository.findByNomeEsame(nomeEsame);
	}
	
	public List<Esame> findByTipoSessione(String tipoSessione){
		return esameRepository.findByTipoSessione(tipoSessione);
	}
	
	public List<Esame> findByDataEsame( Date dataEsame){
		return esameRepository.findByDataEsame(dataEsame);
	}
	
	public Esame findById( Long id) {
		return esameRepository.findById(id).get();
	}
	
	public List<Esame> findAll() {
		List<Esame> esami = new LinkedList<Esame>();
		for(Esame esame: esameRepository.findAll()) {
			esami.add(esame);
		}
		return esami;
	}
	
	public void saveStudente(Studente studente, Long idEsame) {
		Esame esame = esameRepository.findById(idEsame).get();
		esame.getStudenti().add(studente);
		this.updateEsame(esame);
	}
	
	public void saveProfessore( Professore professore , Long idEsame) {
		Esame esame = esameRepository.findById(idEsame).get();
		esame.setProfessore(professore);
		this.updateEsame(esame);
	}
	
	public void saveCorso( Corso corso, Long idEsame) {
		Esame esame = esameRepository.findById(idEsame).get();
		esame.setCorso(corso);
		this.updateEsame(esame);
	}
	public boolean existsByNomeEsameAndTipoSessioneAndDataEsame( String nomeEsame, String tipoSessione, Date dataEsame) {
		return esameRepository.existsByNomeEsameAndTipoSessioneAndDataEsame(nomeEsame, tipoSessione, dataEsame);
	}
}
