package br.brainshare.data.rdb;

import java.util.List;

import lib.exceptions.DAOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.brainshare.data.IDAOQuestion;
import br.brainshare.model.Answer;
import br.brainshare.model.Question;

public class DAOHibernateQuestion implements IDAOQuestion {

	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public List<Question> listAll() throws DAOException {
		try {
			Criteria lista = session.createCriteria(Question.class);

			@SuppressWarnings("unchecked")
			List<Question> questions = lista.list();
			return questions;
		} catch (Exception e) {
			throw new DAOException ("Erro ao listar questões no DAO.");
		}
	}

	@Override
	public void save(Question question) throws DAOException {
		try {
			this.session.save(question);
		} catch (Exception e) {
			throw new DAOException ("Erro ao salvar questão no DAO.");
		}
	}

	@Override
	public boolean findQuestion(Question question) throws DAOException {

		try {
			Question questionr = (Question) session.createCriteria(Question.class)
					.add(Restrictions.or(Restrictions.eq("title", question.getTitle()), Restrictions.eq("question", question.getQuestion())))
					.uniqueResult();

			if(questionr == null){
				return false;
			} else {
				return true;	
			}
		} catch (Exception e) {
			throw new DAOException("Erro ao procurar questão no DAO.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> findQuestionByTitleOrDescription(String title, String desc) throws DAOException{

		try {
			List<Question> lista = session.createCriteria(Question.class)
					.add(Restrictions.or(
							Restrictions.like("title", "%"+title+"%"),
							Restrictions.like("question", "%"+desc+"%")
							)).list();

			System.out.println("lista: "+lista.get(0).getTitle());

			return lista;
		} catch (Exception e) {
			throw new DAOException ("Erro ao buscar questão por título ou descrição no DAO.");
		}
	}

	@Override
	public Integer countByAnswer(Integer id) throws DAOException{

		try {
			Long teste = id.longValue();
			Integer numberAnswer = (Integer) session.createCriteria(Answer.class)
					.add(Restrictions.eq("id", teste))
					.setProjection(Projections.rowCount()).uniqueResult();
			return numberAnswer;
		} catch (Exception e) {
			throw new DAOException ("Erro ao contar por respostas no DAO.");
		}
	}

	@Override
	public Question getQuestionInstance(Question q) throws DAOException {

		try {
			Question questionInstance = (Question) session.createCriteria(Question.class)
					.add(Restrictions.or(Restrictions.eq("title", q.getTitle()), Restrictions.eq("question", q.getQuestion())))
					.uniqueResult();
			return questionInstance;
		} catch (Exception e) {
			throw new DAOException ("Erro ao procurar instância de questão no DAO.");
		}
	}
	@Override
	public void delete(Question q) throws DAOException{
		try {
			this.session.delete(q);
		} catch (Exception e) {
			throw new DAOException ("Erro ao deletar questão no DAO.");
		}
	}

	@Override
	public Question getQuestionInstance(String title) throws DAOException {

		try {
			Question questionInstance = (Question) session.createCriteria(Question.class)
					.add(Restrictions.eq("title", title))
					.uniqueResult();
			return questionInstance;
		} catch (Exception e) {
			throw new DAOException ("Erro ao pegar instancia de questão por título no DAO.");
		}
	}

	@Override
	public Question editQuestion(Question q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Question q) throws DAOException {
		try {
		this.session.update(q);
		} catch (Exception e) {
			throw new DAOException ("Erro ao atualizar questão no DAO.");
		}
	}


}
