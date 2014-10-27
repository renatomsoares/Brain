package br.brainshare.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;

import br.brainshare.HibernateUtil;

public class ConnectionHibernateFilter implements Filter {

	private SessionFactory sf;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			this.sf.getCurrentSession().beginTransaction();
			chain.doFilter(req, resp);
			
			this.sf.getCurrentSession().getTransaction().commit();
			this.sf.getCurrentSession().close();
		} catch(Throwable e){
			try{
				if(this.sf.getCurrentSession().getTransaction().isActive()){
					this.sf.getCurrentSession().getTransaction().rollback();
				}
			} catch(Throwable ex){
				ex.printStackTrace();
			}
			throw new ServletException();
		}
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		this.sf = HibernateUtil.getSession();
	}

}
