package photoalbum.app.web.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.web.form.ProfileRegistrationForm;

public class ProfileRegistrationFormValidator implements Validator {
	
	@Autowired
	private ProfileService profileService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfileRegistrationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ProfileRegistrationForm form = (ProfileRegistrationForm)target;
		
		if(profileService.isUserWithEmailExist(form.getEmail())) {
			errors.rejectValue("email", "form.user.email.exist", "User with email already exists");
		}
		
	}
	
}
