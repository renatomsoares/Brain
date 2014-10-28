package br.brainshare.data.rdb;

import java.util.List;

import lib.exceptions.DAOException;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import br.brainshare.data.IDAOScore;
import br.brainshare.model.Question;
import br.brainshare.model.Score;

public class DAOHibernateScore implements IDAOScore {

	private Session session;
	
	public Session getSession(){
		return session;
	}
	
	public void setSession(Session session){
		this.session = session;
	}
	
	@Override
	public void save(Score score) throws DAOException {
		try {
			this.session.save(score);
		} catch (Exception e) {
			throw new DAOException ("Erro ao salvar score no DAO.");
		}
	}
	
	@Override
	public Integer getScoreByQuestion(Question question) throws DAOException {
		try {
			Criteria lista = session.createCriteria(Score.class);
			Criteria lista2 = lista.createCriteria("question");
			lista2.add(Restrictions.eq("id", question.getId()));


			@SuppressWarnings("unchecked")
			List<Score> score = lista.list();
			Integer total = 0;
			for (int i = 0 ; i < score.size()-1 ; i++) {
				total = total + score.get(i).getScore();
			}
			return total;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException ("Erro ao pegar total de scores por question no DAO.");
		}
	}
	
	@Override
	public Score getScoreInstance(Score score) throws DAOException {
		try {
			Score scoreInstance = (Score) session
					.createCriteria(Score.class)
					.add(Restrictions.eq("id", score.getId())).uniqueResult();
			return scoreInstance;
		} catch (Exception e) {
			throw new DAOException ("Erro ao pegar instância de score no DAO.");
		}
	}

}
