package br.brainshare.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import lib.exceptions.DAOException;
import lib.exceptions.UserException;

import br.brainshare.business.IServiceUser;
import br.brainshare.business.ServiceUser;
import br.brainshare.model.User;

@ManagedBean(name = "userController")
@RequestScoped
public class UserController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private IServiceUser service = new ServiceUser();
	private String passwordVal;

	protected static final String CREDENTIAL = "usuarioLogado";

	public UserController() {
		user = new User();
	}

	public String getPasswordVal() {
		return passwordVal;
	}

	public void setPasswordVal(String passwordVal) {
		this.passwordVal = passwordVal;
	}

	public String save() {
		try {
			if (!user.getPassword().equals(passwordVal) || service.findUser(user)) {
				if(!user.getPassword().equals(passwordVal)){
					FacesMessage msg = new FacesMessage("Senhas diferentes");
					FacesContext.getCurrentInstance().addMessage("erro", msg);
					
				} else {
					FacesMessage msg = new FacesMessage("Já existe um usuário com esse email");
					FacesContext.getCurrentInstance().addMessage("erro", msg);
				}
				return null;
			} else {
				user.setDateRegister(new Date());
				service.save(user);
			}
		} catch (UserException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return login();
	}

	public String login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			if (service.findUserLogin(user)) {
				this.user = service.getUserInstance(user);
				HttpSession sessaoHttp = (HttpSession) facesContext
						.getExternalContext().getSession(true);
				sessaoHttp.setAttribute(CREDENTIAL, user);
				facesContext.getExternalContext().redirect("http://localhost:8080/BrainShare/pages/principal.jsf");
 
			} else {
				/* Cria uma mensagem. */
				FacesMessage msg = new FacesMessage("Usuário ou senha inválido!");
				/*
				 * Obtém a instancia atual do FacesContext e adiciona a mensagem de
				 * erro nele.
				 */
				FacesContext.getCurrentInstance().addMessage("erro", msg);
				return null;
			}
		} catch (UserException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "principal";
	}

	public boolean isLoggedIn() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(CREDENTIAL) != null;
	}

	// para recuperar o usuário na sessão:
	// usuario =
	// (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	public void logout() {
		this.user = null;
		HttpSession sessaoHttp = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);
		sessaoHttp.removeAttribute(CREDENTIAL);
		sessaoHttp.invalidate();
		FacesContext faces = FacesContext.getCurrentInstance();  
        ExternalContext context = faces.getExternalContext();  
        context.invalidateSession();
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(context.getRequestContextPath() + "/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
