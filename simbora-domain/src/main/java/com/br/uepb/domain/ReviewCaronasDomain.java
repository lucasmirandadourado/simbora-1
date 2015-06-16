package com.br.uepb.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="REVIEWCARONAS")
public class ReviewCaronasDomain {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ReviewDomain caronaSeguraTranquila;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ReviewDomain caronaNaoFuncionou;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ReviewDomain getCaronaSeguraTranquila() {
		return caronaSeguraTranquila;
	}
	public void setCaronaSeguraTranquila(ReviewDomain caronaSeguraTranquila) {
		this.caronaSeguraTranquila = caronaSeguraTranquila;
	}
	public ReviewDomain getCaronaNaoFuncionou() {
		return caronaNaoFuncionou;
	}
	public void setCaronaNaoFuncionou(ReviewDomain caronaNaoFuncionou) {
		this.caronaNaoFuncionou = caronaNaoFuncionou;
	}
	
	

}
