package com.eduservice.demo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void saveStudente (Utente utente) {
		Studente studente = new Studente();
		studente.setNomeStudente(utente.getNome());
		studente.setCognomeStudente(utente.getCognome());
		studente.setMatricolaStudente(utente.getUsername());
		studenteRepository.save(studente);
	}
	
	@Transactional
	public void deleteStudente( Long id) {
		Studente studente = studenteRepository.findById(id).get();
		studenteRepository.delete(studente);
	}
	
	@Transactional
	public void deleteAllStudenti() {
		studenteRepository.deleteAll();
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
	
	public List<Studente> findByCognomeStudente( String cognomeStudente){
		return studenteRepository.findByCognomeStudente(cognomeStudente);
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
}
