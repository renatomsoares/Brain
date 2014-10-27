package br.brainshare.business;

import java.util.List;

import lib.exceptions.DAOException;
import lib.exceptions.UserException;
import br.brainshare.data.IDAOUser;
import br.brainshare.model.User;
import br.brainshare.util.DAOFactory;

public class ServiceUser implements IServiceUser{

	
	private static ServiceUser singleton = null;
	private IDAOUser daoUsuario;
	
	public ServiceUser(){
		this.daoUsuario = DAOFactory.createUserDAO();
	}
	
	public static ServiceUser getInstance() {
		if (singleton == null) {
			singleton = new ServiceUser();
		}
		return singleton;
	}
	
	
	public List<User> listAllUser() throws UserException, DAOException {
		return this.daoUsuario.listAll();
	}

	
	public void save(User user) throws UserException, DAOException {

		if (user.getUsername() == "") {
			throw new UserException("Usuario com dados vazios!");
		} else {
			try {
				this.daoUsuario.save(user);
			}
			catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean findUser(User user) throws UserException, DAOException {
		return this.daoUsuario.findUser(user); 
	}

	@Override
	public User getUserInstance(User user) throws UserException, DAOException {
		return this.daoUsuario.getUserInstance(user);
	}

	@Override
	public boolean findUserLogin(User user) throws UserException, DAOException {
		return this.daoUsuario.findUserLogin(user);
	}	
}
