package br.brainshare.data.rdb;

import java.util.List;

import lib.exceptions.DAOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.brainshare.data.IDAOUser;
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
			throw new DAOException ("Erro ao listar usuários no DAO.");
		}
	}

	@Override
	public void save(User user) throws DAOException {
		try {
			this.session.save(user);
		} catch (Exception e) {
			throw new DAOException ("Erro ao salvar usuário no DAO.");
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
			throw new DAOException ("Erro ao procurar usuário no DAO.");
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
			throw new DAOException ("Erro ao pegar instância de usuário no DAO.");
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
			throw new DAOException ("Erro ao procurar usuário no DAO.");
		}
	}

}

