package br.brainshare.suggestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import lib.exceptions.DAOException;
import br.brainshare.model.Question;

public class DAOSuggestionByDescription implements IDAOSuggestionStrategy{

	private Session session;
	
	public DAOSuggestionByDescription(Session session) {
		this.session = session;
	}

	public Question getQuestionInstance(String title) throws DAOException {

		try {
			Question questionInstance = (Question) session.createCriteria(Question.class)
					.add(Restrictions.eq("title", title))
					.uniqueResult();
			return questionInstance;
		} catch (Exception e) {
			throw new DAOException ("Erro ao pegar instancia de quest√£o por t√≠tulo no DAO.");
		}
	}
	
	@Override
	public List<Question> getSuggestions(String title)
			throws DAOException {
		try {
			Criteria lista = session.createCriteria(Question.class);
			List<Question> lista_=new ArrayList<Question>();
			Question q = getQuestionInstance(title);
			
			String[] desc_ = q.getQuestion().split(" ");
			for(int i=0; i < desc_.length; i++) {
				lista.add(Restrictions.ilike("question", desc_[i] , MatchMode.ANYWHERE)
						).list();

				lista_.addAll(lista.list());


			}

			Set set = new HashSet(lista_);
			ArrayList uniqueList = new ArrayList(set);

			System.out.println(uniqueList.size());
			uniqueList.remove(getQuestionInstance(title));
			return uniqueList;
		} 
		catch (Exception e) {
			throw new DAOException ("Erro ao buscar quest„o por tÌtulo ou descriÁ„o no DAO.");
		}
	}
}
