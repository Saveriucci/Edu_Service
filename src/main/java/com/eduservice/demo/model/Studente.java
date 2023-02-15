package com.eduservice.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
	
	@ManyToMany(mappedBy = "studenti")
	private Set<Esame> esami;
	
	@OneToOne
	private Utente utente;
	
	public Studente() {
		this.esami = new HashSet<Esame>();
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
	

	public Set<Esame> getEsami() {
		return esami;
	}

	public void setEsami(Set<Esame> esami) {
		this.esami = esami;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
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
