package br.brainshare.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import lib.exceptions.DAOException;
import lib.exceptions.TagException;
import br.brainshare.business.IServiceTag;
import br.brainshare.business.ServiceTag;
import br.brainshare.model.Tag;

@ManagedBean(name = "tagsController")
@RequestScoped 
public class TagsController {


	private Tag tag;
	private List<Tag> lista = null;
	private boolean list;

	private IServiceTag service = new ServiceTag();


	public TagsController() {
		tag = new Tag();
		setList(false);
	}

	

	public String save() throws TagException, DAOException{
		try {
			service.searchTag(tag.getName());
			FacesMessage msg = new FacesMessage("A tag informada já existe.");
			FacesContext.getCurrentInstance().addMessage("erro", msg);
			return null;
		}
		catch(TagException e){
			service.save(tag);
			return "index";
		}
	}


	public Tag getTag() {
		return tag;
	}



	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public List<Tag> getLista() {
		if(lista == null){
			try {
				lista = service.getTags();
			} catch (TagException e) {
				e.printStackTrace();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}



	public boolean getList() {
		return list;
	}



	public void setList(boolean list) {
		this.list = list;
	}
	
	public void truelist(){
		this.list = true;
		
	}
}	
