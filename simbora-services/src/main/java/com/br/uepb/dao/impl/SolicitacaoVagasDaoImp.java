package com.br.uepb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.br.uepb.dao.SolicitacaoVagasDao;
import com.br.uepb.domain.SolicitacaoVagasDomain;
import com.br.uepb.util.HibernateUtil;


public class SolicitacaoVagasDaoImp implements SolicitacaoVagasDao{ 

	@Override
	public void save(SolicitacaoVagasDomain solicitacaoVagas) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(solicitacaoVagas);
		t.commit();
		HibernateUtil.closedSession();
	}

	@Override
	public SolicitacaoVagasDomain getSolicitacaoVagas(String id) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			SolicitacaoVagasDomain solicitacao = (SolicitacaoVagasDomain) session.get(SolicitacaoVagasDomain.class, Integer.parseInt(id));
			HibernateUtil.closedSession();
			return solicitacao;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<SolicitacaoVagasDomain> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<SolicitacaoVagasDomain> lista = session.createQuery("from SolicitacaoVagasDomain").list();
		t.commit();
		HibernateUtil.closedSession();
		return lista;
	}

	@Override
	public void remove(SolicitacaoVagasDomain solicitacaoVagas) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(solicitacaoVagas);
		t.commit();
		HibernateUtil.closedSession();
	}

	@Override
	public void update(SolicitacaoVagasDomain solicitacaoVagas) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(solicitacaoVagas);
		t.commit();
		HibernateUtil.closedSession();
	}
	
	@Override
	public void excluirTudo() {  
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.createQuery("delete from SolicitacaoVagasDomain where id <> null").executeUpdate();
		t.commit();
		HibernateUtil.closedSession();
    } 
	
	public int getId(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Integer> id = session.createQuery("SELECT MAX(id) FROM SolicitacaoVagasDomain").list();
		t.commit();
		HibernateUtil.closedSession();
		return id.get(0);
	}
	
	public List<SolicitacaoVagasDomain> solicitacoesDoMotorista(String login) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Integer> lista = session.createQuery("select id from CaronaDomain where idUsuario=\'"+login+"\'").list();
		List<SolicitacaoVagasDomain> solicitacoes = session.createQuery("from SolicitacaoVagasDomain").list();
		List<SolicitacaoVagasDomain> s = new ArrayList<>();
		for (SolicitacaoVagasDomain solicitacao : solicitacoes) {
			if(lista.contains(Integer.parseInt(solicitacao.getIdCarona())) && solicitacao.getStatus().equals("Pendente")){
				s.add(solicitacao);
			}
		}
		t.commit();
		HibernateUtil.closedSession();
		return s;
	}

	public List<SolicitacaoVagasDomain> getSolicitacoesRespondidasDoMotorista(
			String login) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Integer> lista = session.createQuery("select id from CaronaDomain where idUsuario=\'"+login+"\'").list();
		List<SolicitacaoVagasDomain> solicitacoes = session.createQuery("from SolicitacaoVagasDomain").list();
		List<SolicitacaoVagasDomain> s = new ArrayList<>();
		for (SolicitacaoVagasDomain solicitacao : solicitacoes) {
			if(lista.contains(Integer.parseInt(solicitacao.getIdCarona())) && !solicitacao.getStatus().equals("Pendente")){
				s.add(solicitacao);
			}
		}
		t.commit();
		HibernateUtil.closedSession();
		return s;
	}
}