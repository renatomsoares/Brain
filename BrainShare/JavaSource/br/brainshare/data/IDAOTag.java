package br.brainshare.data;

import java.util.List;

import lib.exceptions.DAOException;
import br.brainshare.model.Tag;

public interface IDAOTag {

	public void save(Tag tag) throws DAOException;
	public List<Tag> getTags() throws DAOException;
	public Tag getTagInstance(Tag tag) throws DAOException;
	public Tag searchTag(String nome) throws DAOException;

}
