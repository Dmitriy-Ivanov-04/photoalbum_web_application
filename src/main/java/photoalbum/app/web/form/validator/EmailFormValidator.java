package photoalbum.app.web.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.web.form.EmailForm;

@Component
public class EmailFormValidator implements Validator {
	
	@Autowired
	private ProfileService profileService;

	@Override
	public boolean supports(Class<?> clazz) {
		return EmailForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		EmailForm form = (EmailForm)target;
		
		if(!profileService.isUserWithEmailExist(form.getEmail())) {
			errors.rejectValue("email", "form.changePass.email.notexist", "User with email already not exists");
		}
		
	}

}
