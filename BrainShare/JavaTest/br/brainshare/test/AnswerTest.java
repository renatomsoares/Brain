package br.brainshare.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import lib.exceptions.AnswerException;
import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;

import org.junit.Test;

import br.brainshare.business.IServiceAnswer;
import br.brainshare.business.ServiceAnswer;
import br.brainshare.model.Answer;
import br.brainshare.model.Question;
import br.brainshare.model.User;

public class AnswerTest {

	@Test
	public void salvarTest() {
		Question q = new Question();
		q.setQuestion("Qual o valor em km/h da velocidade da luz?");
		q.setTitle("Fisica");
		Answer a = new Answer();
		User u = new User();
		a.setAnswer("Acho que é 300000km/h");
		a.setQuestion(q);
		a.setUser(u);
		a.setDateRegister(new Date());
		
		IServiceAnswer rn = new ServiceAnswer();
		try {
			rn.save(a);
		} catch (AnswerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(true, true);
	}

}
