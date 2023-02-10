package com.eduservice.demo.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Studente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeStudente;
	
	@NotBlank
	private String cognomeStudente;
	
	@NotNull
	private int matricolaStudente;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Esame> esami;
	
	public Studente() {
		this.esami = new LinkedList<Esame>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeStudente() {
		return nomeStudente;
	}

	public void setNomeStudente(String nomeStudente) {
		this.nomeStudente = nomeStudente;
	}

	public String getCognomeStudente() {
		return cognomeStudente;
	}

	public void setCognomeStudente(String cognomeStudente) {
		this.cognomeStudente = cognomeStudente;
	}

	public int getMatricolaStudente() {
		return matricolaStudente;
	}

	public void setMatricolaStudente(int matricolaStudente) {
		this.matricolaStudente = matricolaStudente;
	}

	
	
	public List<Esame> getEsami() {
		return esami;
	}

	public void setEsami(List<Esame> esami) {
		this.esami = esami;
	}

	@Override
	public boolean equals( Object obj) {
		Studente studente = (Studente) obj;
		return this.getMatricolaStudente() == studente.getMatricolaStudente();
	}
	
	@Override
	public int hashCode() {
		return this.getMatricolaStudente();
	}

}
