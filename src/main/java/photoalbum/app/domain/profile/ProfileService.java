package photoalbum.app.domain.profile;

import java.util.List;

import photoalbum.app.domain.dto.ProfileJsonDTO;
import photoalbum.app.domain.model.Profile;
import photoalbum.app.web.form.ProfileRegistrationForm;

public interface ProfileService {
	
	List<Profile> getList();

	boolean isUserWithEmailExist(String email);

	boolean createUserFromRegistrationForm(ProfileRegistrationForm userForm);
	
	Profile getProfileByEmail(String email);
	
	boolean activateProfile(String code);
	
	public List<ProfileJsonDTO> usersByUserAsJson(Long profileId, String divId);
	
	Profile findById(Long profileId);
}
