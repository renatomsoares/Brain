package br.brainshare.suggestion;

import java.util.List;


import lib.exceptions.DAOException;
import br.brainshare.model.Question;

public class SuggestionContext {
	private IDAOSuggestionStrategy strategy;
	
	public SuggestionContext(IDAOSuggestionStrategy strategy) {
		this.strategy = strategy;
	}
	
	public List<Question> executeStrategy(String a, String b) throws DAOException {
		return this.strategy.getSuggestions(a, b);
	}
}
