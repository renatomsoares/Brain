package br.brainshare.business;

import java.util.List;

import lib.exceptions.DAOException;
import lib.exceptions.TagException;
import br.brainshare.data.IDAOTag;
import br.brainshare.model.Tag;
import br.brainshare.util.DAOFactory;

public class ServiceTag implements IServiceTag {
	
	private IDAOTag daoTag = DAOFactory.createTagDAO();
	
	private static ServiceTag singleton = null;
	
	public static ServiceTag getInstance(){
		if (singleton == null) {
			singleton = new ServiceTag();
		}
		return singleton;
	}

	public List<Tag> getTags() throws TagException, DAOException {
		return this.daoTag.getTags();
	}

	@Override
	public void save(Tag tag) throws TagException, DAOException {
		if (tag.getName() == "") {
			throw new TagException("Tag vazia!");
		} else {
			try {
				this.daoTag.save(tag);
			} catch (DAOException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public Tag getTagInstance(Tag tagInstance) throws TagException, DAOException {
		return this.daoTag.getTagInstance(tagInstance);
	}
	
	@Override
	public Tag searchTag(String nome) throws TagException, DAOException {
		if(this.daoTag.searchTag(nome)!= null){
			return this.daoTag.searchTag(nome);
		}else{
			throw new TagException("Tag inexistente!");
		}
	}

}
