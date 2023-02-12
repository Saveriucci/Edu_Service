package com.eduservice.demo.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Esame {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeEsame;
	
	@NotBlank
	@Size(min = 1, max = 100)
	private String descrizioneEsame;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataEsame;
	
	@NotBlank
	@Size(min = 6, max = 9) // estiva, invernale, autunnale
	private String tipoSessione;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Studente> studenti;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Professore professore;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Corso corso;
	
	public Esame() {
		this.studenti = new LinkedList<Studente>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeEsame() {
		return nomeEsame;
	}

	public void setNomeEsame(String nomeEsame) {
		this.nomeEsame = nomeEsame;
	}

	public String getDescrizioneEsame() {
		return descrizioneEsame;
	}

	public void setDescrizioneEsame(String descrizioneEsame) {
		this.descrizioneEsame = descrizioneEsame;
	}

	public Date getDataEsame() {
		return dataEsame;
	}

	public void setDataEsame(Date dataEsame) {
		this.dataEsame = dataEsame;
	}

	public String getTipoSessione() {
		return tipoSessione;
	}

	public void setTipoSessione(String tipoSessione) {
		this.tipoSessione = tipoSessione;
	}

	public List<Studente> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<Studente> studenti) {
		this.studenti = studenti;
	}

	public Professore getProfessore() {
		return professore;
	}

	public void setProfessore(Professore professore) {
		this.professore = professore;
	}
	
	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}

	@Override
	public boolean equals(Object obj) {
		Esame esame = (Esame) obj;
		return this.getNomeEsame().equals(esame.getNomeEsame()) && this.getTipoSessione().equals(esame.getTipoSessione())
				&& this.getDataEsame().equals(esame.getDataEsame());
	}
	
	@Override
	public int hashCode() {
		return this.getNomeEsame().hashCode() + this.getTipoSessione().hashCode() + this.getDataEsame().hashCode();
	}
}
