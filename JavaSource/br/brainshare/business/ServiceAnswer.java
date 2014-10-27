package br.brainshare.business;

import java.util.List;

import lib.exceptions.AnswerException;
import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import br.brainshare.data.IDAOAnswer;
import br.brainshare.model.Answer;
import br.brainshare.model.Question;
import br.brainshare.util.DAOFactory;

public class ServiceAnswer implements IServiceAnswer {

	private IDAOAnswer daoAnswer;

	private static ServiceAnswer singleton = null;
	
	public ServiceAnswer(){
		this.daoAnswer = DAOFactory.createAnswerDAO();
	}
	
	public static ServiceAnswer getInstance(){
		if(singleton == null){
			singleton = new ServiceAnswer();
		}
		return singleton;
	}
	
	@Override
	public void save(Answer resp) throws AnswerException, EmptyFieldException, DAOException {
		if (resp.getAnswer() == "") {
			throw new EmptyFieldException("A resposta não pode ser vazia.");
		}
		else {
			try {
				this.daoAnswer.save(resp);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Answer> listAll(Question question) throws AnswerException, DAOException {
		return this.daoAnswer.listAll(question);
	}

}
