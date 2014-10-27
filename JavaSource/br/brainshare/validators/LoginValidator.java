package br.brainshare.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import lib.exceptions.DAOException;
import lib.exceptions.UserException;

import br.brainshare.business.IServiceUser;
import br.brainshare.business.ServiceUser;
import br.brainshare.model.User;

public class LoginValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		User user = (User) value;
		IServiceUser service = new ServiceUser();
		boolean validator;
		try {
			validator = service.findUser(user);
		
		
		if(!validator){
			FacesMessage message = new FacesMessage();
			message.setDetail("UsuÃ¡rio " + user + " nÃ£o existe!");
			message.setSummary("Login incorreto");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
		} catch (UserException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}
