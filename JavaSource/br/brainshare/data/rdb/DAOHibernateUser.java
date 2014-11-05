package br.brainshare.data.rdb;

import java.util.List;

import lib.exceptions.DAOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.brainshare.data.IDAOUser;
import br.brainshare.model.Answer;
import br.brainshare.model.Question;
import br.brainshare.model.Score;
import br.brainshare.model.User;

public class DAOHibernateUser implements IDAOUser {

	private Session session;
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public List<User> listAll() throws DAOException{
		try {
			Criteria lista = session.createCriteria(User.class);
	
			@SuppressWarnings("unchecked")
			List<User> user = lista.list();
			return user;
		} catch (Exception e) {
			throw new DAOException ("Erro ao listar usu√°rios no DAO.");
		}
	}

	@Override
	public void save(User user) throws DAOException {
		try {
			this.session.save(user);
		} catch (Exception e) {
			throw new DAOException ("Erro ao salvar usu·rio no DAO.");
		}
	}

	@Override
	public boolean findUser(User user) throws DAOException {
		try {
			User userAdd = (User) session.createCriteria(User.class)
					.add(Restrictions.eq("email",user.getEmail()))
		            .uniqueResult();
			
			if(userAdd == null){
				return false;
			} else {
				return true;	
			}
		} catch (Exception e) {
			throw new DAOException ("Erro ao procurar usu·rio no DAO.");
		}
	}

	@Override
	public User getUserInstance(User user) throws DAOException {
		try {
			User userInstance = (User) session.createCriteria(User.class)
					.add(Restrictions.and(Restrictions.eq("username",user.getUsername()),Restrictions.eq("password",user.getPassword())))
		            .uniqueResult();
			return userInstance;
		} catch (Exception e) {
			throw new DAOException ("Erro ao pegar inst‚ncia de usu·rio no DAO.");
		}
	}

	@Override
	public boolean findUserLogin(User user) throws DAOException {
		try {
			User userAdd = (User) session.createCriteria(User.class)
					.add(Restrictions.and(Restrictions.eq("username",user.getUsername()),Restrictions.eq("password",user.getPassword())))
		            .uniqueResult();
			
			if(userAdd == null){
				return false;
			} else {
				return true;	
			}
		} catch (Exception e) {
			throw new DAOException ("Erro ao procurar usu·rio no DAO.");
		}
	}
	
	@Override
	public boolean jaPontuouAPergunta(User user, Question question) {
		Score scoreInstance = (Score) session
				.createCriteria(Score.class)
				.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("question", question)).uniqueResult();
		if (scoreInstance != null) {
			return true;
		} else
			return false;		
	}
	
	@Override
	public boolean jaPontuouAResposta(User user, Answer answer) {
		Score scoreInstance = (Score) session
				.createCriteria(Score.class)
				.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("answer", answer)).uniqueResult();
		if (scoreInstance != null) {
			return true;
		} else
			return false;		
	}

}

