package com.eduservice.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Dipartimento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeDipartimento;
	
	@OneToMany( cascade = CascadeType.ALL)
	private List<Corso> corsi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDipartimento() {
		return nomeDipartimento;
	}

	public void setNomeDipartimento(String nomeDipartimento) {
		this.nomeDipartimento = nomeDipartimento;
	}

	public List<Corso> getCorsi() {
		return corsi;
	}

	public void setCorsi(List<Corso> corsi) {
		this.corsi = corsi;
	}
	
	@Override
	public int hashCode() {
		return this.getNomeDipartimento().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Dipartimento dipartimento = (Dipartimento) obj;
		return this.getNomeDipartimento().equals(dipartimento.getNomeDipartimento());
	}
}
