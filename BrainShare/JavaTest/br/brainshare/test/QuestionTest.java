package br.brainshare.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.brainshare.HibernateUtil;
import br.brainshare.model.Question;

public class QuestionTest {
	
	private static Session session;
	private static Transaction transaction;
	
	@BeforeClass
	public static void openConnection(){
		session = HibernateUtil.getSession().getCurrentSession();
		transaction = session.beginTransaction();
	}
	
	@AfterClass
	public static void closeConnection(){
		
		try {
			transaction.commit();
		} catch (Throwable e) {
			System.err.println("Deu problema no commit: " + e.getMessage());
		}finally{
			try {
				if (session.isOpen()) {
					session.close();
				}
			} catch (Exception e2) {
				System.err.println("Deu problema ao fechar uma conexão: " + e2.getMessage());
			}
		}
	}
	
	@Before
	public void setup(){
		Question q1 = new Question();
		q1.setQuestion("o que é hibernate?");
		q1.setTitle("informatica");
		
		session.save(q1);
		
	}
	
	@After
	public void cleanDados(){
		Criteria lista = session.createCriteria(Question.class);
		@SuppressWarnings("unchecked")
		List<Question> questions = lista.list();
		
		for (Question question : questions) {
			session.delete(question);
		}
	}
	
	@Test
	public void saveQuestionTest(){
		Query find = pesquisar("inf");
		
		Question qFind = (Question) find.uniqueResult();
		assertEquals("informatica", qFind.getTitle());
	}
	
	@Test
	public void listQuestionTest(){
		Criteria lista = session.createCriteria(Question.class);
		@SuppressWarnings("unchecked")
		List<Question> questions = lista.list();
		
		assertEquals(5, questions.size());
	}
	
	@Test
	public void alterQuestionTest(){
		Query find = pesquisar("futebol");
		Question qAlterado = (Question) find.uniqueResult();
		qAlterado.setQuestion("Questão de futebol alterada!");
		session.update(qAlterado);
		
		qAlterado = (Question) find.uniqueResult();
		assertEquals("Questão de futebol alterada!", qAlterado.getQuestion());
	}
	
	@Test
	public void deleteQuestionTest(){
		Query find = pesquisar("portugues");
		Question qDeletado = (Question) find.uniqueResult();
		session.delete(qDeletado);
		
		qDeletado = (Question) find.uniqueResult();
		
		assertNull(qDeletado);
		
	}
	
	private Query pesquisar(String title) {
		String sql = "from Question q where q.title like :title";
		Query find = session.createQuery(sql);
		find.setString("title", "%"+title+"%");
		return find;
	}
}
