package com.br.uepb.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Classe que define o local de origem, local de destino, data da carona,
 * horário de saída e a quantidade de vaga no carro.
 * 
 * @author Lucas Miranda
 * @author Bruno José Clementino
 *
 */

@Entity
@Table(name="CARONA")
public class CaronaDomain {
	
	@Id
	private String id;
	private String origem;
	private String destino;
	private String data;
	private String hora;
	private String vagas;
	private String idUsuario;
	/**
	 * Método construtor default.
	 */
	public CaronaDomain() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the origem
	 */
	public String getOrigem() {
		return origem;
	}
	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}
	/**
	 * @return the vagas
	 */
	public String getVagas() {
		return vagas;
	}
	/**
	 * @param vagas the vagas to set
	 */
	public void setVagas(String vagas) {
		this.vagas = vagas;
	}
	/**
	 * @return the idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
