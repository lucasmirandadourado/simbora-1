package com.br.uepb.domain;

/**
 * A sess�o � aonde ser� guardada duas informa��es b�sicas do 
 * usu�rio do sistema. Ser�o gerados um identificador da sess�o, 
 * tamb�m guardada um identificador do usu�rio e o status 
 * do usuario.
 * 
 * @author Lucas Miranda e Bruno Clementino
 *
 */
public class SessaoDomain {
	
	private String idSessao;
	private String idUsuario;
	
	private boolean statusSessao = false;

	/**
	 * @return the idSessao
	 */
	public String getIdSessao() {
		return idSessao;
	}

	/**
	 * @param idSessao the idSessao to set
	 */
	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
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

	/**
	 * @return the statusSessao
	 */
	public boolean isStatusSessao() {
		return statusSessao;
	}

	/**
	 * @param statusSessao the statusSessao to set
	 */
	public void setStatusSessao(boolean statusSessao) {
		this.statusSessao = statusSessao;
	}

	
}