package br.brainshare.data;

import java.util.List;

import lib.exceptions.DAOException;
import br.brainshare.model.User;

public interface IDAOUser {

	public List<User> listAll() throws DAOException;
	public void save(User user) throws DAOException;
	public boolean findUser(User user) throws DAOException;
	public User getUserInstance(User user) throws DAOException;
	public boolean findUserLogin(User user) throws DAOException;
}
