package br.brainshare.data;

import java.util.List;

import lib.exceptions.DAOException;
import br.brainshare.model.Answer;
import br.brainshare.model.Question;

public interface IDAOAnswer {
	
	public void save(Answer resposta) throws DAOException;
	public List<Answer> listAll(Question question) throws DAOException;
	
}
