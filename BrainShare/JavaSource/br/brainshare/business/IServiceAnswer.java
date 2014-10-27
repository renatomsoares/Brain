package br.brainshare.business;

import java.util.List;

import lib.exceptions.AnswerException;
import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import br.brainshare.model.Answer;
import br.brainshare.model.Question;

public interface IServiceAnswer {
	
	public void save(Answer resp) throws AnswerException, EmptyFieldException, DAOException;
	public List<Answer> listAll(Question question) throws AnswerException, DAOException;
	
}
