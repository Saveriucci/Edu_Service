package com.eduservice.demo.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Professore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeProfessore;
	
	@NotBlank
	private String cognomeProfessore;
	
	@OneToMany(mappedBy = "professore", cascade = CascadeType.ALL)
	private List<Esame> esami;

	
	public Professore() {
		this.esami = new LinkedList<Esame>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeProfessore() {
		return nomeProfessore;
	}

	public void setNomeProfessore(String nomeProfessore) {
		this.nomeProfessore = nomeProfessore;
	}

	public String getCognomeProfessore() {
		return cognomeProfessore;
	}

	public void setCognomeProfessore(String cognomeProfessore) {
		this.cognomeProfessore = cognomeProfessore;
	}

	public List<Esame> getEsami() {
		return esami;
	}

	public void setEsami(List<Esame> esami) {
		this.esami = esami;
	}
	
	@Override
	public int hashCode() {
		return this.getNomeProfessore().hashCode() + this.getCognomeProfessore().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Professore professore = (Professore) obj;
		return this.getNomeProfessore().equals(professore.getNomeProfessore()) && this.getCognomeProfessore().equals(professore.getCognomeProfessore());
	}

}
