package com.br.uepb.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import com.br.uepb.accept.SimboraEasyAccept;
import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.business.PerfilBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.SolicitacaoPontoDeEncontroBusiness;
import com.br.uepb.business.SolicitacaoVagasBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.constants.CaronaException;
import com.br.uepb.constants.PerfilException;
import com.br.uepb.constants.SessaoException;

public class SolicitacaoVagasTest {

	// Tests US04
	UsuarioBusiness usuario;
	CaronaBusiness carona;
	SessaoBusiness sessao;
	SolicitacaoPontoDeEncontroBusiness solicitarVagas;
	SolicitacaoVagasBusiness solicitacao;
	PerfilBusiness perfilBusiness;
	String caronaID1 = "",caronaID2 = "";
	String sessaoID1 = "",sessaoID2 = "";
	String solicitacaoID1 = "",solicitacaoID2 = "";

	@Before
	public void inicializar() throws CaronaException, SessaoException {
		usuario = new UsuarioBusiness();
		carona = new CaronaBusiness();
		sessao = new SessaoBusiness();
		solicitarVagas = new SolicitacaoPontoDeEncontroBusiness();
		solicitacao = new SolicitacaoVagasBusiness();
		perfilBusiness = new PerfilBusiness();
		new SimboraEasyAccept().zerarSistema();
		
		usuario.criarUsuario("lucas", "1uc@5", "Lucas Miranda",
				"João Dourado, Bahia", "lucas@facebook.com");
		usuario.criarUsuario("bruno", "brun0", "Bruno Clementino",
				"Alagoa Nova, Paraíba", "bruno@facebook.com");
		

		sessaoID1 = sessao.abrirSessao("lucas", "1uc@5");
		sessaoID2 = sessao.abrirSessao("bruno", "brun0");
		
		caronaID1=carona.cadastrarCarona(sessaoID1, "Cajazeiras", "Patos", "20/07/2013", "14:00", "4");
		caronaID2=carona.cadastrarCarona(sessaoID2, "Campina Grande", "João Pessoa", "12/09/2013", "21:00", "2");
		carona.definirCaronaPreferencial(caronaID2);
		
	}

	@Test
	public void solicitarVagaTest() throws CaronaException{
	
		try {
			solicitacaoID1 = solicitacao.solicitarVaga(sessaoID2, caronaID1);
			
		} catch (Exception e) {
			fail();
		}
		
		try {
			solicitacao.aceitarSolicitacao(sessaoID1, solicitacaoID1);
		} catch (Exception e) {
			fail();
		}
		
		//Tenta aceitar a solicitacao novamente
		try {
			solicitacao.aceitarSolicitacao(sessaoID1, solicitacaoID1);
		} catch (Exception e) {
			assertEquals("Solicitação já foi aceita", e.getMessage());
		}
		
		try {
			solicitacaoID2 = solicitacao.solicitarVaga(sessaoID2, caronaID1);
			
		} catch (Exception e) {
			fail();
		}
		
		try {
			solicitacao.rejeitarSolicitacao(sessaoID1, solicitacaoID2);
		} catch (Exception e) {
			fail();
		}
		//tenta recusar a solicitação novamente
		try {
			solicitacao.rejeitarSolicitacao(sessaoID1, solicitacaoID2);
		} catch (Exception e) {
			assertEquals("Solicitação inexistente", e.getMessage());
		}
		//tenta aceitar a solicitação recusada
		try {
			solicitacao.aceitarSolicitacao(sessaoID1, solicitacaoID2);
		} catch (Exception e) {
			assertEquals("Solicitação foi recusada", e.getMessage());
		}
		
		assertEquals("Lucas Miranda", solicitacao.getAtributo(solicitacaoID1, "Dono da carona"));
		assertEquals("Bruno Clementino", solicitacao.getAtributo(solicitacaoID1, "Dono da solicitacao"));
		try {
			solicitacao.getAtributo(solicitacaoID1, "Ponto de Encontro");
			fail();//Deve ocorrer uma excessao, pois nenhum ponto de encontro foi definido
		} catch (Exception e) {
		}
		
		
		/*try {
			solicitacaoID2 = solicitacao.solicitarVaga(sessaoID1, caronaID2);
			
		} catch (CaronaException e) {
			assertEquals("Usuário não está na lista preferencial da carona", e.getMessage());
		}
		
		//Fazendo review da carona para que o usuario ser preferencial
		/*try {
			perfilBusiness.reviewVagaEmCarona(sessaoID1, "lucas", "segura e tranquila");
		} catch (PerfilException e) {
			fail();
		}
		
		try {
			solicitacaoID2 = solicitacao.solicitarVaga(sessaoID1, caronaID2);
		} catch (CaronaException e) {
			fail();
		}*/
	}
	
	/*@Test
	public void criarUsuario() {

		String caronaID1 = "",caronaID2 = "",caronaID3 = "",caronaID4 = "",caronaID5="",caronaID6="";
		String solicitacaoID1 = "",solicitacaoID2 = "",solicitacaoID3 = "",solicitacaoID4 = "",solicitacaoID5="",solicitacaoID6="";
		usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");

		try {
			sessao.abrirSessao("mark", "m@rk");
		} catch (SessaoException e) {
			e.getMessage();
		}

		// Cadastro de caronas
		// Cadastro 1
		try {
			caronaID1=carona.cadastrarCarona("mark", "Cajazeiras", "Patos", "20/07/2013",
					"14:00", "4");
		} catch (CaronaException e) {
			e.getMessage();
		}
		// Cadastro 2
		try {
			caronaID2=carona.cadastrarCarona("mark", "São Francisco", "Palo Alto",
					"12/09/2013", "21:00", "2");
		} catch (CaronaException e) {
			e.getMessage();
		}
		// Casdastro 3
		try {
			caronaID3=carona.cadastrarCarona("mark", "Campina Grande", "João Pessoa",
					"01/06/2013", "12:00", "1");
		} catch (CaronaException e) {
			e.getMessage();
		}
		// Casdastro 4
		try {
			caronaID4=carona.cadastrarCarona("mark", "Campina Grande", "João Pessoa",
					"02/06/2013", "12:00", "3");
		} catch (CaronaException e) {
			e.getMessage();
		}
		// Casdastro 5
		try {
			caronaID5=carona.cadastrarCarona("mark", "Campina Grande", "João Pessoa",
					"04/06/2013", "16:00", "2");
		} catch (CaronaException e) {
			e.getMessage();
		}
		// Casdastro 6
		try {
			caronaID6=carona.cadastrarCarona("mark", "Leeds", "londres", "10/02/2013",
					"10:00", "3");
		} catch (CaronaException e) {
			e.getMessage();
		}

		sessao.encerrarSessao("mark");

		usuario.criarUsuario("bill", "billz@o", "Willian Henry Gates III",
				"Medina, Washington", "billzin@gmail.com"); // Gmail para Bill
															// foi ...

		// Resposta de Bill
		try {
			sessao.abrirSessao("bill", "billz@o");
		} catch (SessaoException e) {
			e.getMessage();
		}

		try {
			sugestaoID1=solicitarVagas.sugerirPontoEncontro("bill", "3",
					"Acude Velho; Hiper Bompreco");
		} catch (Exception e) {
			e.getMessage();
		}

		sessao.encerrarSessao("bill");

		try {
			sessao.abrirSessao("mark", "m@rk");
		} catch (SessaoException e) {
			e.getMessage();
		}

		try {
			solicitarVagas.responderSugestaoPontoEncontro("mark", "3", "0PE",
					"Acude Velho;Parque da Crianca");
		} catch (Exception e) {
			e.getMessage();
		}
		sessao.encerrarSessao("mark");

		// Resposta de Bill
		try {
			sessao.abrirSessao("bill", "billz@o");
		} catch (SessaoException e) {
			e.getMessage();
		}
		// idSolicitacao = 0PE
		solicitarVagas.solicitarVagaPontoEncontro("bill", "3", "Acude Velho");

		try {
			assertEquals("Campina Grande",
					solicitarVagas.getAtributoSolicitacao("0PE", "origem"));
		} catch (CaronaException e) {
			e.getMessage();
		}
		try {
			assertEquals("João Pessoa",
					solicitarVagas.getAtributoSolicitacao("0PE", "destino"));
		} catch (CaronaException e) {
			e.getMessage();
		}

		try {
			assertEquals("Mark Zuckerberg",
					solicitarVagas.getAtributoSolicitacao("0PE",
							"Dono da carona"));
		} catch (CaronaException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		try {
			assertEquals("Willian Henry Gates III",
					solicitarVagas.getAtributoSolicitacao("0PE",
							"Dono da solicitacao"));
		} catch (CaronaException e) {
			e.getMessage();
		}

		try {
			assertEquals("Acude Velho", solicitarVagas.getAtributoSolicitacao(
					"0PE", "Ponto de Encontro"));
		} catch (CaronaException e) {
			e.getMessage();
		}

		sessao.encerrarSessao("bill");

		// Aceitar a requisição
		try {
			sessao.abrirSessao("mark", "m@rk");
		} catch (SessaoException e) {
			e.getMessage();
		}

		try {
			solicitarVagas.aceitarSolicitacaoPontoEncontro("mark", "0PE");
		} catch (Exception e) {
			e.getMessage();
		}

		try {
			assertEquals("2",
					solicitarVagas.getAtributoSolicitacao("0PE", "vagas"));
		} catch (CaronaException e) {
			e.getMessage();
		}

		try {
			assertEquals("Acude Velho", solicitarVagas.getAtributoSolicitacao(
					"0PE", "Ponto de Encontro"));
		} catch (CaronaException e) {
			e.getMessage();
		}

		// Abrir sessao de Bill
		try {
			sessao.abrirSessao("bill", "billz@o");
		} catch (SessaoException e) {
			e.getMessage();
		}

		try {
			solicitarVagas.aceitarSolicitacaoPontoEncontro("bill", "0PE");
		} catch (Exception e) {
			assertEquals("Solicitação inexistente", e.getMessage());
		}
		try {
			assertEquals("2", carona.getAtributoCarona("3", "vagas"));
		} catch (CaronaException e) {
			e.getMessage();
		}

		// Sugerir ponto de encontro
		try {
			solicitarVagas.sugerirPontoEncontro("bill", "4",
					"Acude Velho; Hiper Bompreco");
		} catch (Exception e) {
			e.getMessage();
		}

		// Resposta a requisição
		try {
			sessao.abrirSessao("mark", "m@rk");
		} catch (SessaoException e) {
			e.getMessage();
		}

		try {
			solicitarVagas.responderSugestaoPontoEncontro("mark", "4", "",
					"Acude Velho;Parque da Crianca");
		} catch (Exception e) {
			e.getMessage();
		}

		// requisitar vaga carona
		try {
			solicitarVagas.desistirRequisicao("bill", "3", "0PE");
		} catch (Exception e) {
			e.getMessage();
		}

		try {
			solicitarVagas.sugerirPontoEncontro("bill", "3", "");
		} catch (Exception e) {
			assertEquals("Ponto Inválido", e.getMessage());
		}

		try {
			solicitarVagas.responderSugestaoPontoEncontro("mark", "3", "0PE",
					"");
		} catch (Exception e) {
			assertEquals("Ponto Inválido", e.getMessage());
		}

		sessao.encerrarSessao("mark");
		sessao.encerrarSessao("bill");

	}

	@Test
	public void todosErros() {

		usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");

		try {
			sessao.abrirSessao("mark", "m@rk");
		} catch (SessaoException e1) {
			e1.getMessage();
		}
		try {
			carona.cadastrarCarona("mark", "Campina Grande", "João Pessoa",
					"22/10/2014", "17:00", "3");
		} catch (CaronaException e) {
			e.getMessage();
		}

		usuario.criarUsuario("bill", "billz@o", "Willian Henry Gates III",
				"Medina, Washington", "billzin@gmail.com");

		try {
			sessao.abrirSessao("bill", "billz@o");
		} catch (SessaoException e) {
			e.getMessage();
		}

		// Test
		try {
			solicitarVagas.sugerirPontoEncontro("bill", "0", " ");
		} catch (Exception e) {
			assertEquals("Ponto Inválido", e.getMessage());
		}
		try {
			solicitarVagas.sugerirPontoEncontro("bill", "0", null);
		} catch (Exception e) {
			assertEquals("Ponto Inválido", e.getMessage());
		}

		try {
			solicitarVagas.responderSugestaoPontoEncontro("bill", "0", "0PE",
					null);
		} catch (Exception e) {
			assertEquals("Ponto Inválido", e.getMessage());
		}

	}

	@Test
	public void zeraSistema() {
		solicitarVagas.encerrarSistema();
	}

	@Test
	public void solicitacoesVagasUS05() {
		
		usuario.criarUsuario("mark", "m@rk", "Mark Zuckerberg",
				"Palo Alto, California", "mark@facebook.com");
		
		try {
			sessao.abrirSessao("mark", "m@rk");
		} catch (SessaoException e) {
			fail();
		}

		// Caddastrar Caronas
		// Carona1ID
		try {
			carona.cadastrarCarona("mark", "Cajazeiras", "Patos", "20/07/2013",
					"14:00", "4");
		} catch (CaronaException e) {
			fail();
		}
		// Carona2D
		try {
			carona.cadastrarCarona("mark", "São Francisco", "Palo Alto",
					"12/09/2013", "21:00", "2");
		} catch (CaronaException e) {
			fail();
		}
		// Carona3ID
		try {
			carona.cadastrarCarona("mark", "Campina Grande", "João Pessoa",
					"01/06/2013", "12:00", "1");
		} catch (CaronaException e) {
			fail();
		}
		// Carona4ID
		try {
			carona.cadastrarCarona("mark", "Campina Grande", "João Pessoa",
					"02/06/2013", "14:00", "3");
		} catch (CaronaException e) {
			fail();
		}
		// Carona5ID
		try {
			carona.cadastrarCarona("mark", "Campina Grande", "João Pessoa",
					"03/06/2013", "16:00", "2");
		} catch (CaronaException e) {
			fail();
		}
		// Carona6ID
		try {
			carona.cadastrarCarona("mark", "Leeds", "Londres", "10/02/2013",
					"10:00", "3");
		} catch (CaronaException e) {
			fail();
		}

		// Encerrar a sessao de Mark
		sessao.encerrarSessao("mark");

		usuario.criarUsuario("bill", "billz@o", "William Henry Gates III",
				"Medina, Washington", "billzin@msn.com");

		try {
			sessao.abrirSessao("bill", "billz@o");
		} catch (SessaoException e) {
			fail();
		}

		try {
			solicitacao.solicitarVaga("bill", "3");
		} catch (Exception e) {
			fail();
		}
		

		try {
			assertEquals("Campina Grande",
					solicitarVagas.getAtributoSolicitacao("0V", "origem"));
		} catch (CaronaException e) {
			fail();
		}

		try {
			assertEquals("João Pessoa",
					solicitarVagas.getAtributoSolicitacao("0V", "destino"));
		} catch (CaronaException e) {
			fail();
		}

		try {
			assertEquals("Mark Zuckerberg",
					solicitarVagas.getAtributoSolicitacao("0V",
							"Dono da carona"));
		} catch (CaronaException e) {
			fail();
		}

		try {
			assertEquals("William Henry Gates III",
					solicitarVagas.getAtributoSolicitacao("0V",
							"Dono da solicitacao"));
		} catch (CaronaException e) {
			fail();
		}

		try {
			sessao.abrirSessao("mark", "m@rk");
		} catch (SessaoException e) {
			fail();
		}

		try {
			solicitacao.aceitarSolicitacao("mark", "0PE");
		} catch (Exception e) {
			fail();
		}
		

		try {
			assertEquals("3", carona.getAtributoCarona("3", "vagas"));
		} catch (CaronaException e) {
			fail();
		}

		// Requisitar vaga na carona.
		try {
			solicitacao.solicitarVaga("bill", "4");
		} catch (Exception e) {
			fail();
		}
		

		try {
			assertEquals("Campina Grande",
					solicitarVagas.getAtributoSolicitacao("1V", "origem"));
		} catch (CaronaException e) {
			fail();
		}
		try {
			assertEquals("João Pessoa",
					solicitarVagas.getAtributoSolicitacao("1V", "destino"));
		} catch (CaronaException e) {
			fail();
		}
		try {
			assertEquals("Mark Zuckerberg",
					solicitarVagas.getAtributoSolicitacao("1V",
							"Dono da carona"));
		} catch (CaronaException e) {
			fail();
		}
		try {
			assertEquals("William Henry Gates III",
					solicitarVagas.getAtributoSolicitacao("1V",
							"Dono da solicitacao"));
		} catch (CaronaException e) {
			fail();
		}

		// Rejeitar requisicao
		try {
			solicitacao.rejeitarSolicitacao("mark", "1V");
		} catch (Exception e) {
			fail();
		}

		try {
			assertEquals("2", carona.getAtributoCarona("4", "vagas"));
		} catch (CaronaException e) {
			fail();
		}

		// Tentar rejeitar novamente a requisição
		try {
			solicitacao.rejeitarSolicitacao("mark", "1V");
		} catch (Exception e) {
			assertEquals("Solicitação inexistente", e.getMessage());
		}

		try {
			assertEquals("2", carona.getAtributoCarona("4", "vagas"));
		} catch (CaronaException e) {
			fail();
		}

		usuario.encerrarSistema();
	}*/

}