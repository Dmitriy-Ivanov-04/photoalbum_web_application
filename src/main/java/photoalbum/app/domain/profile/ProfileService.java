package photoalbum.app.domain.profile;

import java.util.List;

import photoalbum.app.domain.model.Profile;
import photoalbum.app.web.form.ProfileRegistrationForm;

public interface ProfileService {
	
	List<Profile> getList();

	boolean isUserWithEmailExist(String email);

	void createUserFromRegistrationForm(ProfileRegistrationForm userForm);
	
	Profile getProfileByEmail(String email);
}
