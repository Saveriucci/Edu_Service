package com.eduservice.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduservice.demo.model.Studente;
import com.eduservice.demo.model.Utente;
import com.eduservice.demo.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void saveUtente(Utente utente) {
		if (utente.getRole() == null) {
			utente.setRole(Utente.DEFAULT_ROLE);
		}
		utente.setPassword(passwordEncoder.encode(utente.getPassword()));
		utenteRepository.save(utente);
	}
	
	@Transactional
	public void saveUtente(Studente studente) {
		Utente utente = new Utente();
		utente.setNome(studente.getNomeStudente());
		utente.setCognome(studente.getCognomeStudente());
		utente.setUsername(studente.getMatricolaStudente());
		utente.setPassword("password"); //password provvisoria
		if (utente.getRole() == null) {
			utente.setRole(Utente.DEFAULT_ROLE);
		}
		utente.setPassword(passwordEncoder.encode(utente.getPassword()));
		utenteRepository.save(utente);
	}
	
	@Transactional
	public void deleteUtente(int matricola) {
		Utente utente = utenteRepository.findByUsername(matricola);
		utenteRepository.delete(utente);
	}
	
	@Transactional
	public void deleteAll() {
		//cancello tutti gli utenti/studenti che non siano amministratori
		List<Utente> utenti = utenteRepository.findByRole(Utente.DEFAULT_ROLE);
		for( Utente utente : utenti) {
			utenteRepository.delete(utente);
		}
	}
	
	@Transactional
	public void updateUtente(Studente studente) {
		Utente utente = utenteRepository.findByUsername(studente.getMatricolaStudente());
		utente.setNome(studente.getNomeStudente());
		utente.setCognome(studente.getCognomeStudente());
		utenteRepository.save(utente);
	}
	
	public boolean existsByUsername( Utente utente) {
		return utenteRepository.existsByUsername(utente.getUsername());
	}
	public boolean existsByMatricola( int matricola) {
		return utenteRepository.existsByUsername(matricola);
	}
	
	
	public Utente getUser(Long id) {
		return utenteRepository.findById(id).get();
	}

	public Utente findByUsername(int matricola) {
		return utenteRepository.findByUsername(matricola);
	}

}
