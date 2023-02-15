package com.eduservice.demo.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Esame;
import com.eduservice.demo.repository.CorsoRepository;
import com.eduservice.demo.repository.EsameRepository;
import com.eduservice.demo.repository.StudenteRepository;

@Service
public class EsameService {

	@Autowired
	EsameRepository esameRepository;

	@Autowired
	CorsoRepository corsoRepository;
	
	@Autowired
	private StudenteRepository studenteRepository;

	@Transactional
	public void saveEsame(Esame esame) {
		esameRepository.save(esame);
	}

	@Transactional
	public void deleteEsame( Long id) {
		esameRepository.deleteById(id);
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

	public void saveCorso( Corso corso, Long idEsame) {
		Esame esame = esameRepository.findById(idEsame).get();
		esame.setCorso(corso);
		this.updateEsame(esame);
	}
	public boolean existsByNomeEsameAndTipoSessioneAndDataEsame( String nomeEsame, String tipoSessione, Date dataEsame) {
		return esameRepository.existsByNomeEsameAndTipoSessioneAndDataEsame(nomeEsame, tipoSessione, dataEsame);
	}

	public void emptyCorso(Esame esame) {
		esame.setCorso(null);
		this.updateEsame(esame);
	}

	public void removeStudente(Long id) {
		for(Esame esame : esameRepository.findAll())
			esame.getStudenti().remove(studenteRepository.findById(id).get());
		
	}
}
