/**
 * 
 */
package com.br.uepb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

/**
 * @author Lucas e Bruno
 *
 */
@Entity
@Table (name="CARONAMUNICIPAL")
public class CaronaMunicipalDomain {
	
	final static Logger logger = Logger.getLogger(CaronaMunicipalDomain.class);
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	private String cidade;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}


	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


}
