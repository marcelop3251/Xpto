package br.com.xpto.entidades;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Cidade {

	
	@Id
	private Integer  idIbge;
	
	@ManyToOne
	@NotNull
	private Estado estado;
	
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Capital capital;
	
	
	private double longitude;
	
	
	private double latitude;
	
	private String nomeAlternativo;
	
	@ManyToOne
	private Regiao regiao;

	public Integer getIdIbge() {
		return idIbge;
	}

	public void setIdIbge(Integer idIbge) {
		this.idIbge = idIbge;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Capital getCapital() {
		return capital;
	}

	public void setCapital(Capital capital) {
		this.capital = capital;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getNomeAlternativo() {
		return nomeAlternativo;
	}

	public void setNomeAlternativo(String nomeAlternativo) {
		this.nomeAlternativo = nomeAlternativo;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	
	
	
	
	
}
