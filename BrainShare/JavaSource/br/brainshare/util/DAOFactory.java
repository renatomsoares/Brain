package br.brainshare.util;

import br.brainshare.HibernateUtil;
import br.brainshare.data.IDAOAnswer;
import br.brainshare.data.IDAOQuestion;
import br.brainshare.data.IDAOTag;
import br.brainshare.data.IDAOUser;
import br.brainshare.data.rdb.DAOHibernateAnswer;
import br.brainshare.data.rdb.DAOHibernateQuestion;
import br.brainshare.data.rdb.DAOHibernateTag;
import br.brainshare.data.rdb.DAOHibernateUser;

public class DAOFactory {
	
	public static IDAOAnswer createAnswerDAO(){
		DAOHibernateAnswer answer = new DAOHibernateAnswer();
		answer.setSession(HibernateUtil.getSession()
				.getCurrentSession());
		return answer;
	}

	public static IDAOUser createUserDAO() {
		DAOHibernateUser user = new DAOHibernateUser();
		user.setSession(HibernateUtil.getSession()
				.getCurrentSession());
		return user;
	}
	
	public static IDAOQuestion createQuestionDAO() {
		DAOHibernateQuestion quest = new DAOHibernateQuestion();
		quest.setSession(HibernateUtil.getSession()
				.getCurrentSession());
		return quest;
	}

	public static IDAOTag createTagDAO() {
		DAOHibernateTag tag = new DAOHibernateTag();
		tag.setSession(HibernateUtil.getSession()
				.getCurrentSession());
		return tag;
	}
}
