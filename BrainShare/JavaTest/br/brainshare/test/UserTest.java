package br.brainshare.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.brainshare.HibernateUtil;
import br.brainshare.business.IServiceUser;
import br.brainshare.business.ServiceUser;
import br.brainshare.model.User;

public class UserTest {

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
	
	
	@Test
	public void loginTest() {
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.and(Restrictions.eq("username","admin"),Restrictions.eq("password","Admin123")))
	            .uniqueResult();
		
		assertEquals(user.getUsername(), "admin");
	}

}
