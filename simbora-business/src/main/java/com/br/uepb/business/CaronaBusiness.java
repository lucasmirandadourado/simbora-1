package com.br.uepb.business;

import java.text.SimpleDateFormat;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.br.uepb.constants.CaronaException;
import com.br.uepb.dao.impl.CaronaDaoImp;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SessaoDomain;

/**
 * Classe responsável por gerenciar a carona. Define as funcionalidades de
 * cadastrar carona, localizar caronas e outras funcionalidades ligadas a
 * carona.
 * 
 * 
 * @author Lucas Miranda e Bruno Clementino
 * 
 */
public class CaronaBusiness {

	public static Logger logger = Logger.getLogger(CaronaBusiness.class);

	public static List<CaronaDomain> caronas = new CaronaDaoImp().list();
	CaronaDomain carona;
	List<SessaoDomain> sessao = SessaoBusiness.getSessoes();
	private static List<CaronaDomain> interesseCaronas = new ArrayList<CaronaDomain>();
	
	/**
	 * Salva todos as caronas e em seguida limpa a List<CaronaDomain>.
	 */
	public void encerrarSistema() {
		for (CaronaDomain carona : caronas) {
			try {
				CaronaDaoImp caronaDaoImp = new CaronaDaoImp();
				caronaDaoImp.save(carona);
			} catch (Exception e) {
				e.getMessage();
			}
		}
		caronas.clear();
	}

	/**
	 * Esse metodo deverá retornar as possíveis caronas cadastradas no sistema.
	 * Dependendo dos valores passados nos parametros deverá ser retornado a
	 * origem e destino que se deseja.
	 * 
	 * @param idSessao
	 *            Identificador da sessão.
	 * @param origem
	 *            Origem da carona.
	 * @param destino
	 * @return <code>Se</code> origem for <code>null</code> então retorna
	 * @throws CaronaException
	 * @throws Exception
	 */
	public String localizarCarona(String idSessao, String origem, String destino)
			throws CaronaException {
		if (idSessao == null) {
			throw new CaronaException("Sessão inválida");
		}

		if (origem.equals("-") || origem.equals("()") || origem.equals("!")
				|| origem.equals("!?")) {
			throw new CaronaException("Origem inválida");
		}

		if (destino.equals(".") || destino.equals("()") || destino.equals("!?")) {
			throw new CaronaException("Destino inválido");
		}

		if (!origem.isEmpty() && !destino.isEmpty()) {
			return origemDestinoCarona(origem, destino);
		}

		if (origem.isEmpty() && destino.isEmpty()) {
			return origemDestinoCarona();
		}

		if (origem.isEmpty() && !destino.isEmpty()) {
			return destinoCarona(destino);
		}

		if (!origem.isEmpty() && destino.isEmpty()) {
			return origemCarona(origem);
		}
		return "";

	}

	/**
	 * Esse método será chamado caso a origem e destino não for vazias!
	 * 
	 * @param origem
	 * @param destino
	 * @return os ids das coronas que correspendem ao mesmo destino e origem.
	 */
	private String origemDestinoCarona(String origem, String destino) {
		String ids = "{";
		boolean flag = true;// indica se a quantidade de ids é 0
		for (CaronaDomain carona : caronas) {
			if (carona.getOrigem().equals(origem)
					&& carona.getDestino().equals(destino)) {
				if (!flag) {
					ids += ",";
				}
				ids += carona.getId();
				flag = false;
			}
			/*if (ids.equals("{0") || ids.equals("{0,")) {
				ids = "{";
				flag = true;
			}*/
		}

		return ids + "}";
	}

	/**
	 * Esse método será chamado se tanto a origem quanto o destino forem
	 * <code>null</code>.
	 * 
	 * @return todos os id's das coronas.
	 */
	private String origemDestinoCarona() {
		String ids = "{";
		boolean flag = true;// indica se a quantidade de ids � 0 (Serve para
							// auxiliar na formata��o da string de retorno
		for (CaronaDomain carona : caronas) {
			if (!flag) {
				ids += ",";
			}
			ids += carona.getId();
			flag = false;
		}

		return ids + "}"; 
	}

	/**
	 * Esse método retorna todos os id's das caronas que têm a mesma origem do
	 * parametro do metodo.
	 * 
	 * @param origem
	 * @return todos os ids da carona que tem origem iguais ao passado pelo
	 *         parametro.
	 */
	private String origemCarona(String origem) {
		String ids = "{";
		boolean flag = true;// indica se a quantidade de ids é 0
		for (CaronaDomain carona : caronas) {
			if (carona.getOrigem().equals(origem)) {
				if (!flag) {
					ids += ",";
				}
				ids += carona.getId();
				flag = false;
			}
		}

		return ids + "}";
	}

	/**
	 * Esse método retorna todos os id's das caronas que têm a mesmo destino do
	 * parametro do metodo.
	 * 
	 * @param destino
	 * @return todos os ids da carona que tem destino iguais ao passado pelo
	 *         parametro.
	 */
	private String destinoCarona(String destino) {
		String ids = "{";
		boolean flag = true;// indica se a quantidade de ids é 0
		for (CaronaDomain carona : caronas) {
			if (carona.getDestino().equals(destino)) {
				if (!flag) {
					ids += ",";
				}
				ids += carona.getIdUsuario();
				flag = false;
			}
		}

		return ids + "}";
	}

	/**
	 * Cadastra a carona e retorna um identificador da carona.
	 * 
	 * @param idSessao
	 * @param origem
	 * @param destino
	 * @param data
	 * @param hora
	 * @param qtdDeVagas
	 * @return idCarona
	 * @throws CaronaException
	 * @throws Exception
	 */
	public String cadastrarCarona(String idSessao, String origem,
			String destino, String data, String hora, String qtdDeVagas)
			throws CaronaException {
		if (idSessao == null || idSessao.trim().isEmpty()) {
			throw new CaronaException("Sessão inválida");
		}
		if (!SessaoBusiness.hasSessao(idSessao)) {
			throw new CaronaException("Sessão inexistente");
		}
		if (origem == null || origem.trim().isEmpty()) {
			throw new CaronaException("Origem inválida");
		}
		if (destino == null || destino.trim().isEmpty()) {
			throw new CaronaException("Destino inválido");
		}
		if (data == null || data.trim().isEmpty()) {
			throw new CaronaException("Data inválida");
		}
		if (!isData(data)) {
			throw new CaronaException("Data inválida");
		}
		if (hora == null || hora.trim().isEmpty()) {
			throw new CaronaException("Hora inválida");
		}
		if (!isHora(hora)) {
			throw new CaronaException("Hora inválida");
		}
		if (qtdDeVagas == null || qtdDeVagas.trim().isEmpty()) {
			throw new CaronaException("Vaga inválida");
		}
		try {
			Integer.parseInt(qtdDeVagas);
		} catch (Exception e) {
			throw new CaronaException("Vaga inválida");
		}

		carona = new CaronaDomain();
		carona.setOrigem(origem);
		carona.setDestino(destino);
		carona.setData(data);
		carona.setHora(hora);
		carona.setVagas(qtdDeVagas);
		carona.setIdUsuario(getUsuario(idSessao));;
		
		CaronaDaoImp dao = new CaronaDaoImp();
		dao.save(carona);
		caronas = dao.list();
		return getIdCaroneiro(carona);

	}

	private String getIdCaroneiro(CaronaDomain carona2) {
		for (CaronaDomain caronaDomain : caronas) {
			if (caronaDomain.getIdUsuario().equals(carona2.getIdUsuario()) 
					&& caronaDomain.getData().equals(carona2.getData()) &&
					caronaDomain.getOrigem().equals(carona2.getOrigem())) {
				return caronaDomain.getId()+"";
			}
		}
		return null;
	}

	private String getUsuario(String idSessao) {
		for (SessaoDomain sessao : SessaoBusiness.getSessoes()) {
			if (sessao.getIdSessao().equals(idSessao)) {
				return sessao.getIdUsuario();
			}
		}
		return null;
	}

	/**
	 * Verifica se o parametro data é um formato da data com o padrão
	 * dia/mes/ano. Sendo que o dia tem dois digitos, o mes dois digitos e ano
	 * quatro digitos. (dd/mm/aaaa)
	 * 
	 * @param data
	 * @return se a data seguir o padrão (dd/mm/aaaa) retorna <code>true</code>,
	 *         caso contrario <code>false</code>
	 */
	@SuppressWarnings("unused")
	private boolean isData(String data) {
		try {
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
			formatoData.setLenient(false);
			Date dataFormatada = formatoData.parse(data);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifica o parametro passado é do formato HH:mm. Sendo que os digitos são
	 * numeros. e mm não pode ser maior que 60.
	 * 
	 * @param data
	 * @return se for um horario retorna <code>true</code>, caso contrario
	 *         <code>false</code>
	 */
	@SuppressWarnings("unused")
	private boolean isHora(String data) {
		try {
			SimpleDateFormat formatoData = new SimpleDateFormat("HH:mm");
			formatoData.setLenient(false);
			Date dataFormatada = formatoData.parse(data);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Retorna o atributo que o usuário precisar (origem, destino, data e hora).
	 * 
	 * @param idCarona
	 * @param atributo
	 * @return
	 * @throws CaronaException
	 */
	public String getAtributoCarona(String idCarona, String atributo)
			throws CaronaException {
		if (idCarona == null || idCarona.trim().isEmpty()) {
			throw new CaronaException("Identificador do carona é inválido");
		}

		if (atributo == null) {
			throw new CaronaException("Atributo inválido");
		}
		if (atributo.trim().isEmpty()) {
			throw new CaronaException("Atributo inválido");
		}
		if (!idCaronaExistir(idCarona)) {
			throw new CaronaException("Item inexistente");
		}

		if (atributo.equals("ehMunicipal")) {
			if (idCarona.contains("m")) {
				return "true";
			}
			return "false";
		}

		if (atributo.equals("origem")) {
			return caronas.get(Integer.valueOf(idCarona)).getOrigem();
		}
		if (atributo.equals("destino")) {
			return caronas.get(Integer.valueOf(idCarona)).getDestino();
		}
		if (atributo.equals("data")) {
			return caronas.get(Integer.valueOf(idCarona)).getData();
		}
		if (atributo.equals("vagas")) {
			return caronas.get(Integer.valueOf(idCarona)).getVagas() + "";
		}

		throw new CaronaException("Atributo inexistente");
	}

	/**
	 * Verifica se existe a carona!
	 * 
	 * @param idCarona
	 * @return {@link Boolean}
	 */
	private boolean idCaronaExistir(String idCarona) {
		for (CaronaDomain carona : caronas) {
			if ((carona.getId() == Integer.parseInt(idCarona))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna a origem e destino da carona.
	 * 
	 * @param idCarona
	 * @return origem - destino
	 * @throws CaronaException
	 */
	public String getTrajeto(String idCarona) throws CaronaException {
		if (idCarona == null) {
			throw new CaronaException("Trajeto Inválida");
		}
		if (idCarona.trim().isEmpty()) {
			throw new CaronaException("Trajeto Inexistente");
		}
		try {
			Integer.valueOf(idCarona);
		} catch (NumberFormatException e) {
			throw new CaronaException("Trajeto Inexistente");
		}
		try {
			return caronas.get(Integer.valueOf(idCarona)).getOrigem()
					+ " - "
					+ caronas.get(Integer.valueOf(idCarona))
							.getDestino();
		} catch (Exception e) {
			throw new CaronaException("");
		}

	}

	/**
	 * Retorna uma frase parecido como se segue abaixo: <br>
	 * <b>Origem</b> para <b>destino</b>, no dia <b>tal</b>, as <b>tal Hora</b>
	 * 
	 * @param idCarona
	 * @return
	 * @throws CaronaException
	 */
	public String getCarona(String idCarona) throws CaronaException {
		if (idCarona == null) {
			throw new CaronaException("Carona Inválida");
		}
		if (idCarona.trim().isEmpty()) {
			throw new CaronaException("Carona Inexistente");
		}
		int valor;
		try {
			valor = Integer.valueOf(idCarona);
			if (valor > caronas.size()) {
				throw new CaronaException("Carona Inexistente");
			}
		} catch (Exception e) {
			throw new CaronaException("Carona Inexistente");
		}

		CaronaDomain carona = caronas.get(Integer.valueOf(idCarona));
		return carona.getOrigem() + " para "
				+ carona.getDestino() + ", no dia " + carona.getData()
				+ ", as " + carona.getHora();
	}

	/**
	 * @return lista de carona.
	 */
	public static List<CaronaDomain> getCaronas() {
		return caronas;
	}

	/**
	 * Seta a lista de {@link CaronaDomain}.
	 * 
	 * @param caronas
	 */
	public static void setCaronas(List<CaronaDomain> caronas) {
		CaronaBusiness.caronas = caronas;
	}

	public CaronaDomain getCarona() {
		return carona;
	}

	public void setCarona(CaronaDomain carona) {
		this.carona = carona;
	}

	public List<SessaoDomain> getSessao() {
		return sessao;
	}

	public void setSessao(List<SessaoDomain> sessao) {
		this.sessao = sessao;
	}

	/**
	 * Diminui a quantidade de vagas da carona.
	 * 
	 * @param idcarona
	 */
	public void reduzQtdVagas(String idcarona) {

		for (CaronaDomain carona : caronas) {
			if (carona.getId() == Integer.parseInt(idcarona)) {
				int qtdVagasAtual = Integer.parseInt(carona.getVagas()) - 1;
				carona.setVagas(qtdVagasAtual + "");
			}
		}
	}

	/**
	 * Aumenta a quantidade de vagas.
	 * 
	 * @param idcarona
	 */
	public void aumentaQtdVagas(String idcarona) {

		for (CaronaDomain carona : caronas) {
			if (carona.getId() == Integer.parseInt(idcarona)) {
				int qtdVagasAtual = Integer.parseInt(carona.getVagas()) + 1;
				carona.setVagas(qtdVagasAtual + "");
			}
		}
	}

	/**
	 * Retorna o id da carona.
	 * 
	 * @param idSessao
	 * @param indexCarona
	 * @return
	 */
	public String getCaronaUsuario(String idSessao, String indexCarona) {
		try {

			int indice = Integer.parseInt(indexCarona) - 1;
			int cont = 0;
			// percorre as caronas para verificar qual a carona x do usuario
			for (CaronaDomain carona : caronas) {
				if ((carona.getId()+"").equals(indexCarona)) {
					if (cont == indice) {
						return carona.getId()+"";
					}
					cont++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Retorna todas as caronas dos usuarios.
	 * 
	 * @param idSessao
	 * @return
	 */
	public String getTodasCaronasUsuario(String idSessao) {
		String ids = "{";
		String idUsuario = "";
		for (SessaoDomain sessao : SessaoBusiness.getSessoes()) {
			if (sessao.getIdSessao().equals(idSessao)) {
				idUsuario = sessao.getIdUsuario();
			}
			
		}
		boolean flag = true;// indica se a quantidade de ids é 0
		for (CaronaDomain carona : caronas) {
			if (carona.getIdUsuario().equals(idUsuario)) {
				if (!flag) {
					ids += ",";
				}
				ids += carona.getId();
				flag = false;
			}
		}
		return ids + "}";
	}

	private String destinoCidade(String cidade) {
		String ids = "{";
		boolean flag = true;// indica se a quantidade de ids é 0
		for (CaronaDomain carona : caronas) {
			if (carona.getOrigem().equals(cidade)) {
				if (!flag) {
					ids += ",";
				}
				ids += carona.getId();
				flag = false;
			}
		}

		return ids + "}";
	}

	/**
	 * @return the interesseCaronas
	 */
	public static List<CaronaDomain> getInteresseCaronas() {
		return interesseCaronas;
	}

	/**
	 * @param interesseCaronas the interesseCaronas to set
	 */
	public static void setInteresseCaronas(List<CaronaDomain> interesseCaronas) {
		CaronaBusiness.interesseCaronas = interesseCaronas;
	}
}
