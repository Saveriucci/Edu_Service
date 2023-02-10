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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Corso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeCorso;
	
	@NotBlank
	@Size(min = 1, max = 1000)
	private String programmaCorso;
	
	@NotNull
	private int cfu;
	
	@OneToMany(mappedBy = "corso", cascade = CascadeType.ALL)
	private List<Esame> esami;

	public Corso() {
		this.esami = new LinkedList<Esame>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCorso() {
		return nomeCorso;
	}

	public void setNomeCorso(String nomeCorso) {
		this.nomeCorso = nomeCorso;
	}

	public String getProgrammaCorso() {
		return programmaCorso;
	}

	public void setProgrammaCorso(String programmaCorso) {
		this.programmaCorso = programmaCorso;
	}

	public int getCfu() {
		return cfu;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;
	}

	public List<Esame> getEsami() {
		return esami;
	}

	public void setEsami(List<Esame> esami) {
		this.esami = esami;
	}

	@Override
	public int hashCode() {
		return this.getNomeCorso().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Corso corso = (Corso) obj;
		return this.getNomeCorso().equals(corso.getNomeCorso());
	}
	
	
	
}
