package br.brainshare.ordinationQuestion;

import java.util.List;

import lib.exceptions.DAOException;
import br.brainshare.model.Question;

public interface IOrdinationStrategy {
	
	List<Question> findQuestions(String title, String desc)
			throws DAOException;
}
