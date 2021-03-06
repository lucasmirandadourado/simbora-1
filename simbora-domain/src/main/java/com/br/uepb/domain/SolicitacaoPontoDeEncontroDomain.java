package com.br.uepb.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.apache.log4j.Logger;


/**
 * 
 * @author Lucas Miranda e Bruno Clementino
 *
 */
@Entity
@Table(name="SOLICITACAOPONTODEENCONTRO")
public class SolicitacaoPontoDeEncontroDomain {
	final static Logger logger = Logger.getLogger(SolicitacaoPontoDeEncontroDomain.class);
	/**
	 * Armazena respectivamente os pontos de sugestão (0), resposta (1) e
	 * confirmação (2).
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL)
    @OrderColumn(name="idPonto")
	private PontoDeEncontroDomain[] pontoDeEncontro = new PontoDeEncontroDomain[3];
	
	@Id
	@GeneratedValue
	@Column(name = "idSugestao")
	private int idSugestao; 
	private boolean emAndamento = true;
		// Se a solicitação ainda não foi concluída

	/**
	 * Retorna o ponto de encontro.
	 * @param indice
	 * @return ponto de encontro.
	 */
	public PontoDeEncontroDomain getPontoDeEncontro(int indice) {
		return pontoDeEncontro[indice];
	}

	/**
	 * Define o ponto de encontro. Dependendo do indice poderá definida como sugestão (0), 
	 * resposta (1) ou confirmação (2).
	 * @param pontoDeEncontro
	 * @param indice
	 */
	public void setPontoDeEncontro(PontoDeEncontroDomain pontoDeEncontro, int indice) {
		this.pontoDeEncontro[indice] = pontoDeEncontro;
	}

	/**
	 * @return id da sugestão.
	 */
	public int getIdSugestao() { 
		return idSugestao;
	}

	/**
	 * Atribui o id da sugestão da carona.
	 * @param idSugestao
	 */
	public void setIdSugestao(int idSugestao) { 
		this.idSugestao = idSugestao;
	}
	
	/**
	 * 
	 * @return status do andamento da sugestão da carona. <code>true</code> ou <code>false</code>.
	 */
	public boolean isEmAndamento() { 
		return emAndamento;
	}

	/**
	 * Define o status da sugestão da carona.
	 * @param emAndamento
	 */
	public void setEmAndamento(boolean emAndamento) { 
		this.emAndamento = emAndamento;
	}
}
