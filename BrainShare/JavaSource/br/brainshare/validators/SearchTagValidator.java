package br.brainshare.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class SearchTagValidator implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		String tagName = (String) value;
		
		if (!(tagName.length() > 1)) {
			FacesMessage message = new FacesMessage();
			message.setSummary("Erro: a tag deve conter mais de uma letra.");
			message.setDetail("A tag deve conter mais de uma letra.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
