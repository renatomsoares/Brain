package br.brainshare.data.rdb;

import java.util.List;


import lib.exceptions.DAOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.brainshare.data.IDAOTag;
import br.brainshare.model.Tag;

public class DAOHibernateTag implements IDAOTag {

	private Session session;

	public Session getSession(){
		return session;
	}

	public void setSession(Session session){
		this.session = session;
	}
	
	@Override
	public void save(Tag tag) throws DAOException{
		try {
			this.session.save(tag);
		}  catch (Exception e) {
			throw new DAOException ("Erro ao salvar tag no DAO.");
		}
		
	}

	@Override
	public List<Tag> getTags() throws DAOException{
		try {
			Criteria lista = session.createCriteria(Tag.class);
	
			@SuppressWarnings("unchecked")
			List<Tag> tags = lista.list();
	
			return tags;
		} catch (Exception e) {
			throw new DAOException ("Erro ao consultar tags no DAO.");
		}
	}

	@Override
	public Tag getTagInstance(Tag tag) throws DAOException {
		try {
			Tag questionInstance = (Tag) session
					.createCriteria(Tag.class)
					.add(Restrictions.eq("name", tag.getName())).uniqueResult();
			return questionInstance;
		} catch (Exception e) {
			throw new DAOException ("Erro ao pegar instância de tag no DAO.");
		}
	}

	@Override
	public Tag searchTag(String nome) throws DAOException {
		try {
			Tag tag = (Tag) session.createCriteria(Tag.class)
					.add(Restrictions.eq("name", nome)).uniqueResult();
	
			return tag;
		} catch (Exception e) {
			throw new DAOException ("Erro ao consultar tag no DAO.");
		}
	}

}
