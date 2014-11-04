package br.brainshare.ordinationQuestion;


import java.util.List;


import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import lib.exceptions.DAOException;
import br.brainshare.model.Question;

public class OrdinationByTime implements IOrdinationStrategy{

	private Session session;
	
	public OrdinationByTime(Session session) {
		this.session = session;
	}
	
	@Override
	public List<Question> findQuestions(String title, String desc) throws DAOException{

		try {
			List<Question> lista = session.createCriteria(Question.class).addOrder(Order.asc("id"))
					.add(Restrictions.or(
							Restrictions.like("title", "%"+title+"%"),
							Restrictions.like("question", "%"+desc+"%")
							)).list();
		

			System.out.println("lista: "+lista.get(0).getTitle());

			return lista;
		} catch (Exception e) {
			throw new DAOException ("Erro ao buscar questão no DAO.");
		}
	}
}
