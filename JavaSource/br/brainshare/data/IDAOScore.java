package br.brainshare.data;

import lib.exceptions.DAOException;
import br.brainshare.model.Question;
import br.brainshare.model.Score;

public interface IDAOScore {

	void save(Score score) throws DAOException;

	Integer getScoreByQuestion(Question question) throws DAOException;

	Score getScoreInstance(Score score) throws DAOException;

}
