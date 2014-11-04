package br.brainshare.suggestion;

import java.util.List;

import lib.exceptions.DAOException;
import br.brainshare.model.Question;

public interface IDAOSuggestionStrategy {
	
	public List<Question> getSuggestions(String title, String desc) throws DAOException;
}
