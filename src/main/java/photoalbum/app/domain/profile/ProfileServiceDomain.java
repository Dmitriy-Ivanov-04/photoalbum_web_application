package photoalbum.app.domain.profile;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.stereotype.Service;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.model.Profile;
import photoalbum.app.domain.model.Role;
import photoalbum.app.web.form.ProfileRegistrationForm;

@Service
public class ProfileServiceDomain implements ProfileService {
	
	@Autowired
	ProfileStorage profileStorage;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Override
	public List<Profile> getList() {
		return profileStorage.findAll();
	}

	@Override
	public boolean isUserWithEmailExist(String email) {
		return profileStorage.countByEmail(email) != 0 ? true : false;
	}

	@Override
	public void createUserFromRegistrationForm(ProfileRegistrationForm userForm) {
		Profile u = new Profile();

        BeanUtils.copyProperties(userForm, u);
        u.setPassword(bCrypt.encode(userForm.getPassword()));
        u.setEnabled(true);
        u.getProfileRoles().add(Role.USER);

        profileStorage.save(u);
	}
	
	public Profile getProfileByEmail(String email) {
		return profileStorage.findByEmailAndEnabledTrue(email);
	}
	
}
