package br.brainshare.ordinationQuestion;

import java.util.List;


import lib.exceptions.DAOException;
import br.brainshare.model.Question;

public class OrdinationContext {
	private IOrdinationStrategy strategy;
	
	public OrdinationContext(IOrdinationStrategy strategy) {
		this.strategy = strategy;
	}
	
	public List<Question> executeStrategy(String title, String desc) throws DAOException {
		return this.strategy.findQuestions(title, desc);
	}
}
