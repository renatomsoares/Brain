package br.brainshare.business;

import lib.exceptions.DAOException;
import br.brainshare.data.IDAOScore;
import br.brainshare.model.Question;
import br.brainshare.model.Score;
import br.brainshare.util.DAOFactory;

public class ServiceScore implements IServiceScore {
	
	private IDAOScore daoScore = DAOFactory.createScoreDAO();
	
	private static ServiceScore singleton = null;
	
	public static ServiceScore getInstance(){
		if (singleton == null) {
			singleton = new ServiceScore();
		}
		return singleton;
	}

	@Override
	public Integer getScoreByQuestion(Question question) throws DAOException {
		return this.daoScore.getScoreByQuestion(question);
	}

	@Override
	public void save(Score score) throws DAOException {
			try {
				this.daoScore.save(score);
			} catch (DAOException e){
				e.printStackTrace();
			}
		}

	@Override
	public Score getScoreInstance(Score scoreInstance) throws DAOException {
		return this.daoScore.getScoreInstance(scoreInstance);
	}
}
