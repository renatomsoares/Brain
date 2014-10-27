package br.brainshare.business;

import java.util.List;

import lib.exceptions.DAOException;
import lib.exceptions.UserException;

import br.brainshare.model.User;

public interface IServiceUser {
	
	public List<User> listAllUser() throws UserException, DAOException;
	public boolean findUser(User user) throws UserException, DAOException;
	public User getUserInstance(User user) throws UserException, DAOException;
	public void save(User user) throws UserException, DAOException;
	public boolean findUserLogin(User user) throws UserException, DAOException;
	
}
