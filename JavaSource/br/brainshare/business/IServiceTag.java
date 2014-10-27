package br.brainshare.business;

import java.util.List;

import lib.exceptions.DAOException;
import lib.exceptions.TagException;
import br.brainshare.model.Tag;

public interface IServiceTag {

	public void save(Tag tag) throws TagException, DAOException;
	public List<Tag> getTags() throws TagException, DAOException;
	public Tag getTagInstance(Tag tagInstance) throws TagException, DAOException;
	public Tag searchTag(String nome) throws TagException, DAOException;
}
