package com.eduservice.demo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduservice.demo.model.Corso;
import com.eduservice.demo.model.Dipartimento;
import com.eduservice.demo.repository.DipartimentoRepository;

@Service
public class DipartimentoService {

	@Autowired
	private DipartimentoRepository dipartimentoRepository;
	
	@Transactional
	public void saveDipartimento( Dipartimento dipartimento) {
		dipartimentoRepository.save(dipartimento);
	}
	
	@Transactional
	public void deleteDipartimento(Long id) {
		Dipartimento dipartimento = dipartimentoRepository.findById(id).get();
		dipartimentoRepository.delete(dipartimento);
	}
	
	@Transactional
	public void deleteAllDipartimenti() {
		dipartimentoRepository.deleteAll();
	}
	
	@Transactional
	public void updateDipartimento( Dipartimento dipartimento) {
		Dipartimento dipartimentoUpdate = dipartimentoRepository.findById(dipartimento.getId()).get();
		dipartimentoUpdate.setNomeDipartimento(dipartimento.getNomeDipartimento());
		dipartimentoUpdate.setCorsi(dipartimento.getCorsi());
		dipartimentoRepository.save(dipartimentoUpdate);
	}
	
	public boolean existsByNomeDipartimento( String nomeDipartimento) {
		return dipartimentoRepository.existsByNomeDipartimento(nomeDipartimento);
	}
	public Dipartimento findById( Long id) {
		return dipartimentoRepository.findById(id).get();
	}
	
	public List<Dipartimento> findAll() {
		List<Dipartimento> dipartimenti = new LinkedList<Dipartimento>();
		for(Dipartimento dipartimento: dipartimentoRepository.findAll()) {
			dipartimenti.add(dipartimento);
		}
		return dipartimenti;
	}
	
	public void saveCorso(Corso corso, Long idDipartimento) {
		Dipartimento dipartimento = dipartimentoRepository.findById(idDipartimento).get();
		dipartimento.getCorsi().add(corso);
		this.updateDipartimento(dipartimento);
	}
}
