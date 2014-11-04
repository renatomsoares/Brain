package br.brainshare.data;

import java.util.List;

import org.hibernate.Session;

import lib.exceptions.DAOException;
import br.brainshare.model.Question;

public interface IDAOQuestion {
	
public void save(Question question) throws DAOException;
	
	public List<Question> listAll() throws DAOException;
	
	public void delete(Question q) throws DAOException;
	
	public boolean findQuestion(Question question) throws DAOException;
	
	public Question getQuestionInstance(Question q) throws DAOException;
	
	public Question editQuestion(Question q) throws DAOException;
	
	public Question getQuestionInstance(String title) throws DAOException;
	
	public List<Question> findQuestionByTitleOrDescription(String title, String desc) throws DAOException;
	
	//public List<Question> findSuggestionTitle(String title, String desc) throws DAOException;
	
	public Integer countByAnswer(Integer id) throws DAOException;
	
	public void update(Question q) throws DAOException;

	public void setScore(Question q, Integer score);

	void setCountAnswer(Question q);

	Session getSession();
	
}
