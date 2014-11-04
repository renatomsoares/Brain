package br.brainshare.business;

import java.util.List;

import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import lib.exceptions.QuestionException;
import br.brainshare.model.Question;

public interface IServiceQuestion {

	public boolean save(Question q) throws EmptyFieldException, QuestionException, DAOException;
	
	public List<Question> listAll() throws QuestionException, DAOException;
	
	public void delete(Question q) throws QuestionException, DAOException;
	
	public boolean findQuestion(Question quest) throws QuestionException, DAOException;
	
	public Question getQuestionInstance(Question question) throws QuestionException, DAOException;
	
	public Question editQuestion(Question q) throws QuestionException, DAOException;
		
	public Question getQuestionInstance(String title) throws QuestionException, DAOException;
	
	public List<Question> findQuestionByTitleOrDescription(String title, String desc) throws QuestionException, DAOException;
	
	public List<Question> findSuggestionTitle(String title, String desc) throws QuestionException, DAOException;

	public void update(Question q) throws QuestionException, DAOException;

	public void setScore(Question q, Integer score);
	
	void countAnswer(Question q) throws DAOException;

	List<Question> findSuggestion(String title, String desc)
			throws QuestionException, DAOException;
}
