package br.brainshare.model;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import lib.exceptions.DAOException;
import lib.exceptions.QuestionException;
import br.brainshare.business.IServiceQuestion;
import br.brainshare.business.ServiceQuestion;

@Entity
@Table(name = "question")
public class Question {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull(message = "Por favor, insira uma questão.")
	private String question;
	
	@NotNull(message = "Por favor, insira um título.")
	private String title;
	
	@NotNull
	@Column(name = "data_cadastro")
	private Date dateRegister;
	
	@ManyToOne
	@JoinColumn(name = "id_tag", nullable = false)
	private Tag tags;
	
	@OneToOne
	@JoinColumn(name = "id_user", nullable = true)
	private User user;
	
	@Column(columnDefinition = "integer default '0'")
	private Integer countAnswer;
	
	@Column(columnDefinition = "integer default '0'")
	private Integer score;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tag getTags() {
		return tags;
	}

	public void setTags(Tag tags) {
		this.tags = tags;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}
	
	public Integer getCountAnswer() {
		//IServiceQuestion service = new ServiceQuestion();
		//this.countAnswer = service.countByAnswer(id); 
		return countAnswer;
	}
	
	public void setCountAnswer(Integer countAnswer) {
		this.countAnswer = countAnswer;
	}
	
	public String show(){
		IServiceQuestion service = new ServiceQuestion();
		Question q = null; 
		try {
			q = service.getQuestionInstance(title);
		} catch (QuestionException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		HttpSession sessaoHttp = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
        sessaoHttp.setAttribute("questaoClicada", q);
		return "question";
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
}
