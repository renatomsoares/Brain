package br.brainshare.data.rdb;

import java.util.List;

import lib.exceptions.DAOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.brainshare.data.IDAOAnswer;
import br.brainshare.model.Answer;
import br.brainshare.model.Question;

public class DAOHibernateAnswer implements IDAOAnswer {

	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public void save(Answer resp) throws DAOException {
		try {
			this.session.save(resp);
		} catch (Exception e) {
			throw new DAOException("Erro ao salvar resposta no DAO.");
		}
	}

	@Override
	public List<Answer> listAll(Question question) throws DAOException {
		try {
			Criteria lista = session.createCriteria(Answer.class);
			Criteria lista2 = lista.createCriteria("question");
			lista2.add(Restrictions.eq("id", question.getId()));


			@SuppressWarnings("unchecked")
			List<Answer> answer = lista.list();
			return answer;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException ("Erro ao listar respostas no DAO.");
		}
	}
}
