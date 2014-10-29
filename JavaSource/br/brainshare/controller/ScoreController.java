package br.brainshare.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lib.exceptions.AnswerException;
import lib.exceptions.DAOException;
import lib.exceptions.EmptyFieldException;
import br.brainshare.business.IServiceQuestion;
import br.brainshare.business.IServiceScore;
import br.brainshare.business.IServiceUser;
import br.brainshare.business.ServiceQuestion;
import br.brainshare.business.ServiceScore;
import br.brainshare.business.ServiceUser;
import br.brainshare.model.Question;
import br.brainshare.model.Score;
import br.brainshare.model.User;

@ManagedBean(name = "scoreController")
@RequestScoped
public class ScoreController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Score score;
	private IServiceScore service = new ServiceScore();
	private IServiceUser serviceUser = new ServiceUser();
	private IServiceQuestion serviceQuestion = new ServiceQuestion();

	public ScoreController() {
		this.score = new Score();
	}

	public String save() throws AnswerException, EmptyFieldException, DAOException {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("usuarioLogado");
		Question question = (Question) session.getAttribute("questaoClicada");

		if (!serviceUser.pontuouPergunta(user, question)) {
			this.score.setUser(user);
			this.score.setQuestion(question);
			this.service.save(score);

			this.serviceQuestion.setScore(question, score.getScore());

			System.out.println("passou aqui");
			return "index";
		} else {
			return "index";
		}
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}


}
