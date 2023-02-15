package com.eduservice.demo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Esame;
import com.eduservice.demo.model.Professore;
import com.eduservice.demo.repository.CorsoRepository;

@Service
public class CorsoService {
	
	@Autowired
	private CorsoRepository corsoRepository;
	
	@Transactional
	public void saveCorso( Corso corso) {
		this.corsoRepository.save(corso);
	}
	
	@Transactional
	public void deleteCorso(Long id) {
		Corso corso = corsoRepository.findById(id).get();
		this.corsoRepository.delete(corso);
	}
	
	@Transactional
	public void deleteAllCorsi() {
		this.corsoRepository.deleteAll();
	}
	
	@Transactional
	public void updateCorso( Corso corso) {
		Corso corsoUpdate = corsoRepository.findById(corso.getId()).get();
		corsoUpdate.setNomeCorso(corso.getNomeCorso());
		corsoUpdate.setProgrammaCorso(corso.getProgrammaCorso());
		corsoUpdate.setEsami(corso.getEsami());
		corsoUpdate.setProfessore(corso.getProfessore());
		this.corsoRepository.save(corsoUpdate);
	}
	
	public boolean existsByNomeCorso( String nomeCorso) {
		return corsoRepository.existsByNomeCorso(nomeCorso);
	}
	
	public Corso findById( Long id) {
		return corsoRepository.findById(id).get();
	}
	
	public List<Corso> findAll() {
		List<Corso> corsi = new LinkedList<Corso>();
		for(Corso corso: corsoRepository.findAll()) {
			corsi.add(corso);
		}
		return corsi;
	}

	public Corso findBynomeCorso(String nomeCorso) {
		return corsoRepository.findByNomeCorso(nomeCorso);
	}

	public void saveProfessore(Professore professore, Corso corso) {
		corso.setProfessore(professore);
		this.updateCorso(corso);
	}

	public Corso findByNomeCorso(String nomeCorso) {
		return corsoRepository.findByNomeCorso(nomeCorso);
	}

	public void removeProfessore(Long idCorso) {
		Corso corso = corsoRepository.findById(idCorso).get();
		corso.setProfessore(null);
		this.updateCorso(corso);
	}
	
	public List<Esame> findEsamiByCognomeProfessore(Professore professore){
		List<Corso> corsi = corsoRepository.findCorsoByProfessore(professore);
		List<Esame> esamiCorsi = new LinkedList<Esame>();
		for(Corso corso : corsi) {
			esamiCorsi.addAll(corso.getEsami());
		}
		return esamiCorsi;
	}

}
