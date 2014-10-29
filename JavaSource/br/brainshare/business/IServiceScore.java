package br.brainshare.business;

import lib.exceptions.DAOException;
import br.brainshare.model.Question;
import br.brainshare.model.Score;
import br.brainshare.model.User;

public interface IServiceScore {

	void save(Score score) throws DAOException;
	Integer getScoreByQuestion(Question question) throws DAOException;
	Score getScoreInstance(Score scoreInstance) throws DAOException;
}
