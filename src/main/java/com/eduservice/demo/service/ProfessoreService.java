package com.eduservice.demo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduservice.demo.model.Professore;
import com.eduservice.demo.repository.ProfessoreRepository;

@Service
public class ProfessoreService {

	@Autowired
	private ProfessoreRepository professoreRepository;
	
	@Transactional
	public void SaveProfessore( Professore professore) {
		professoreRepository.save(professore);
	}
	
	@Transactional
	public void deleteProfessore ( Long id) {
		professoreRepository.findById(id).get().getCorsi().clear();
		professoreRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteAllProfessori() {
		professoreRepository.deleteAll();
	}
	
	@Transactional
	public void updateProfessore( Professore professore) {
		Professore professoreUpdate = professoreRepository.findById(professore.getId()).get();
		professoreUpdate.setNomeProfessore(professore.getNomeProfessore());
		professoreUpdate.setCognomeProfessore(professore.getCognomeProfessore());
		professoreRepository.save(professoreUpdate);
	}
	
	public Professore findByCognomeProfessore(String cognomeProfessore) {
		return professoreRepository.findByCognomeProfessore(cognomeProfessore);
	}
	
	public Professore findById( Long id) {
		return professoreRepository.findById(id).get();
	}
	
	public List<Professore> findAll() {
		List<Professore> professori = new LinkedList<Professore>();
		for(Professore professore: professoreRepository.findAll()) {
			professori.add(professore);
		}
		return professori;
	}

	public boolean existsByCognomeProfessore(String cognomeProfessore) {
		return professoreRepository.existsByCognomeProfessore(cognomeProfessore);
	}
}
