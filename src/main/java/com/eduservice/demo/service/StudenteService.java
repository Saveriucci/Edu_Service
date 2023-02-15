package com.eduservice.demo.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduservice.demo.model.Esame;
import com.eduservice.demo.model.Studente;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.repository.StudenteRepository;

@Service
public class StudenteService {

	
	@Autowired
	private StudenteRepository studenteRepository;
	
	@Transactional
	public void saveStudente (Studente studente) {
		studenteRepository.save(studente);
	}
	
	@Transactional
	public Studente saveStudente (Utente utente) {
		Studente studente = new Studente();
		studente.setNomeStudente(utente.getNome());
		studente.setCognomeStudente(utente.getCognome());
		studente.setMatricolaStudente(utente.getUsername());
		studente.setUtente(utente);
		studenteRepository.save(studente);
		return studente;
	}
	
	@Transactional
	public int deleteStudente( Long id) {
		Studente studente = studenteRepository.findById(id).get();
		int matricola = studente.getMatricolaStudente();
		studente.getEsami().clear();
		studenteRepository.delete(studente);
		return matricola;
	}
	
	@Transactional
	public void updateStudente(Studente studente) {
		Studente studenteUpdate = studenteRepository.findById(studente.getId()).get();
		studenteUpdate.setNomeStudente(studente.getCognomeStudente());
		studenteUpdate.setCognomeStudente(studente.getCognomeStudente());
		studenteUpdate.setMatricolaStudente(studente.getMatricolaStudente());
		studenteUpdate.setEsami(studente.getEsami());
		studenteRepository.save(studenteUpdate);
	}
	
	public boolean existsByMatricola(int matricola) {
		return studenteRepository.existsByMatricolaStudente(matricola);
	}
	
	public Studente findByMatricola( int matricola) {
		return studenteRepository.findByMatricolaStudente(matricola);
	}
	
	public List<Studente> findByNomeStudente( String nomeStudente){
		return studenteRepository.findByNomeStudente(nomeStudente);
	}
	
	public Studente findById( Long id) {
		return studenteRepository.findById(id).get();
	}
	
	public List<Studente> findAll() {
		List<Studente> studenti = new LinkedList<Studente>();
		for(Studente studente: studenteRepository.findAll()) {
			studenti.add(studente);
		}
		return studenti;
	}

	public Set<Esame> getEsamiStudenteByIdStudente(Long idStudente) {
		Studente studente= studenteRepository.findById(idStudente).get();
		return studente.getEsami();
	}
}
