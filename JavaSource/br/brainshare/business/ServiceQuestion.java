package br.brainshare.business;

import java.util.List;

import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import lib.exceptions.QuestionException;
import br.brainshare.data.IDAOQuestion;
import br.brainshare.model.Question;
import br.brainshare.ordinationQuestion.IOrdinationStrategy;
import br.brainshare.ordinationQuestion.OrdinationByAnswersNumber;
import br.brainshare.ordinationQuestion.OrdinationByTime;
import br.brainshare.ordinationQuestion.OrdinationContext;
import br.brainshare.suggestion.DAOSuggestionByTitle;
import br.brainshare.suggestion.SuggestionContext;
import br.brainshare.util.DAOFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;















import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import lib.exceptions.QuestionException;
import br.brainshare.data.IDAOQuestion;
import br.brainshare.model.Question;
import br.brainshare.suggestion.DAOSuggestionByTitle;
import br.brainshare.suggestion.IDAOSuggestionStrategy;
import br.brainshare.suggestion.SuggestionContext;
import br.brainshare.util.DAOFactory;



public class ServiceQuestion implements IServiceQuestion{

	private static ServiceQuestion singleton = null;

	private IDAOQuestion daoQuestion;

	private SuggestionContext contextSuggestion;

	private OrdinationContext contextOrdination;

	public ServiceQuestion() {
		this.daoQuestion = DAOFactory.createQuestionDAO();
		try {

			Object objectSuggestion = createClassByProperties("suggestion","br.brainshare.suggestion.DAOSuggestionBy");
			Object objectOrdination = createClassByProperties("ordination","br.brainshare.ordinationQuestion.OrdinationBy");
		
			
			contextSuggestion = new SuggestionContext((IDAOSuggestionStrategy) objectSuggestion);
			contextOrdination = new OrdinationContext((IOrdinationStrategy) objectOrdination);

		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
	
	public Object createClassByProperties(String criterio ,String classname) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Properties prop = getProp();
		String config = prop.getProperty(criterio);
		System.out.println("a sugestao eh por : ----->>"+config);
		//String classname = "br.brainshare.suggestion.DAOSuggestionBy";
		classname += config;
		System.out.println(classname);
		Class c = Class.forName(classname);
		Constructor cons = c.getConstructor(Session.class);
		Object object = cons.newInstance(daoQuestion.getSession());
		return object;
	}
	
	public static ServiceQuestion getInstance() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
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
			throw new QuestionException ("QuestÃ£o nao encontrada!");
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
			throw new QuestionException ("Questão não encontrada.");
		}
		else if (daoQuestion.getQuestionInstance(q.getTitle()) != null) {
			throw new QuestionException("O título informado já existe.");
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
	public List<Question> findSuggestion(String title)
			throws QuestionException, DAOException {
		return contextSuggestion.executeStrategy(title);
	}	

	public static Properties getProp() throws IOException { 
		Properties props = new Properties(); 

		FileInputStream file = new FileInputStream( "C:/Users/Renato/Documents/Brain2.0/src/properties/config.properties"); 
		props.load(file);

		return props; 
	}
}
