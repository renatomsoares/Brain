package br.brainshare.controller;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import lib.exceptions.QuestionException;
import lib.exceptions.TagException;
import br.brainshare.business.IServiceQuestion;
import br.brainshare.business.IServiceTag;
import br.brainshare.business.ServiceQuestion;
import br.brainshare.business.ServiceTag;
import br.brainshare.model.Question;
import br.brainshare.model.Tag;
import br.brainshare.model.User;

@ManagedBean(name = "questionController")
@RequestScoped
public class QuestionController {

	private Question quest;
	private Tag tagInstance;
	private List<Question> lista = null;
	private List<Question> listaTag = null;
	private List<Question> listByTitleOrDesc = null;
	private List<Question> listSuggestionByTitle = null;
	private IServiceQuestion service = new ServiceQuestion();
	private IServiceTag sTag = new ServiceTag();
	private String titleOrDesc = "";

	public QuestionController() {
		quest = new Question();
		tagInstance = new Tag();
	}

	public String save() throws DAOException {

		quest.setDateRegister(new Date());
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		User user = (User) session.getAttribute("usuarioLogado");
		quest.setUser(user);
		quest.setScore(0);
		quest.setCountAnswer(0);

		try {
			sTag.searchTag(tagInstance.getName());

			tagInstance = sTag.getTagInstance(tagInstance);
		} catch (TagException e1) {
			e1.printStackTrace();
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		tagInstance.incrementQtd();
		System.out.println(tagInstance.getName());

		quest.setTags(tagInstance);

		try {
			service.save(quest);
		} catch (QuestionException e) {
			FacesMessage msg = new FacesMessage("Já existe uma pergunta com esse título.");
			FacesContext.getCurrentInstance().addMessage("erro", msg);
			return null;
		} catch (EmptyFieldException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}

		return "index";
	}

	public String find() {
		return "findQuestion";
	}

	public Tag getTagInstance() {
		return tagInstance;
	}

	public void setTagInstance(Tag tagInstance) {
		this.tagInstance = tagInstance;
	}

	public Question getQuest() {
		return quest;
	}

	public void setQuest(Question quest) {
		this.quest = quest;
	}

	public List<Question> getLista() {
		if (lista == null) {
			try {
				lista = service.listAll();
			} catch (QuestionException e) {
				e.printStackTrace();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	public List<Question> getListByTitleOrDesc() {
		if (listByTitleOrDesc == null) {
			try {
				listByTitleOrDesc = service.findQuestionByTitleOrDescription(
						titleOrDesc, titleOrDesc);
			} catch (QuestionException e) {
				e.printStackTrace();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return listByTitleOrDesc;
	}
	
	public List<Question> getListSuggestionByTitle(String Sugtitle,String SugDesc) {
		System.out.println("aqui " + Sugtitle + " "+ SugDesc);
		if (listSuggestionByTitle == null) {
			try {
				listSuggestionByTitle = service.findSuggestionTitle(
						Sugtitle, SugDesc);
			} catch (QuestionException e) {
				e.printStackTrace();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return listSuggestionByTitle;
	}

	public String getTitleOrDesc() {
		return titleOrDesc;
	}

	public void setTitleOrDesc(String titleOrDesc) {
		this.titleOrDesc = titleOrDesc;
	}

	public List<Question> getListaTag() {
		return listaTag;
	}

	public void setListaTag(List<Question> listaTag) {
		this.listaTag = listaTag;
	}
}