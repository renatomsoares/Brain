package br.brainshare.business;

import java.util.List;

import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import lib.exceptions.QuestionException;
import br.brainshare.data.IDAOQuestion;
import br.brainshare.model.Question;
import br.brainshare.ordinationQuestion.OrdinationByAnswersNumber;
import br.brainshare.ordinationQuestion.OrdinationByTime;
import br.brainshare.ordinationQuestion.OrdinationContext;
import br.brainshare.suggestion.DAOSuggestionByTitle;
import br.brainshare.suggestion.SuggestionContext;
import br.brainshare.util.DAOFactory;



public class ServiceQuestion implements IServiceQuestion{

	private static ServiceQuestion singleton = null;

	private IDAOQuestion daoQuestion;

	private SuggestionContext contextSuggestion;

	private OrdinationContext contextOrdination;

	public ServiceQuestion(){
		this.daoQuestion = DAOFactory.createQuestionDAO();
		contextSuggestion = new SuggestionContext(new DAOSuggestionByTitle(daoQuestion.getSession()));
		contextOrdination = new OrdinationContext(new OrdinationByAnswersNumber(daoQuestion.getSession()));
	}
	public static ServiceQuestion getInstance(){
		if(singleton==null){
			singleton = new ServiceQuestion();
		}
		return singleton;
	}

	@Override
	public List<Question> listAll() throws QuestionException, DAOException {
		return this.daoQuestion.listAll();

	}

	@Override
	public void delete(Question q)throws QuestionException, DAOException {
		if (!daoQuestion.findQuestion(q)) {
			throw new QuestionException ("Questão nao encontrada!");
		} 
		else {
			try {
				this.daoQuestion.delete(q);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean save(Question q) throws EmptyFieldException, QuestionException, DAOException {

		if (daoQuestion.getQuestionInstance(q.getTitle()) != null) {
			throw new QuestionException ("Não pode existir mais de uma questão com o mesmo título.");
		} 
		else {
			try {
				this.daoQuestion.save(q);
			} catch (DAOException e) {
				e.printStackTrace();		
			}
		}
		return true;
	}

	@Override
	public Question getQuestionInstance(Question question) throws QuestionException, DAOException {
		return this.daoQuestion.getQuestionInstance(question);
	}

	@Override
	public boolean findQuestion(Question quest) throws QuestionException, DAOException {
		return this.daoQuestion.findQuestion(quest);
	}

	@Override
	public Question editQuestion(Question q) throws QuestionException {
		return null;
	}
	@Override
	public Question getQuestionInstance(String title) throws QuestionException, DAOException {
		return this.daoQuestion.getQuestionInstance(title);
	}
	@Override
	public List<Question> findQuestions(String title,
			String desc) throws QuestionException, DAOException {
		return contextOrdination.executeStrategy(title, desc);
	}


	@Override
	public void countAnswer(Question q) throws DAOException {
		q.setCountAnswer(q.getCountAnswer()+1);
		this.daoQuestion.update(q);
	}

	@Override
	public void setScore(Question q, Integer score) {
		Integer novoScore = q.getScore() + score; 
		q.setScore(novoScore);
		try {
			this.daoQuestion.update(q);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Question q) throws QuestionException, DAOException {
		if (!daoQuestion.findQuestion(q)) {
			throw new QuestionException ("Questão nao encontrada!");
		}
		else if (daoQuestion.getQuestionInstance(q.getTitle()) != null) {
			throw new QuestionException("Este título já existe!");
		} 
		else {
			try {
				this.daoQuestion.update(q);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	@Override
	public List<Question> findSuggestionTitle(String title, String desc)
			throws QuestionException, DAOException {
		return daoQuestion.findSuggestionTitle(title, desc);
	}
	 */

	@Override
	public List<Question> findSuggestion(String title, String desc)
			throws QuestionException, DAOException {
		return contextSuggestion.executeStrategy(title, desc);
	}	

}
