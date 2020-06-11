package photoalbum.app.domain.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.mail.MailClient;
import photoalbum.app.domain.dto.ProfileJsonDTO;
import photoalbum.app.domain.model.Profile;
import photoalbum.app.domain.model.Role;
import photoalbum.app.web.form.ProfileRegistrationForm;

@Service
public class ProfileServiceDomain implements ProfileService {
	
	@Autowired
	ProfileStorage profileStorage;
	
	@Autowired
	MailClient mailClient;
		
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
	public boolean createUserFromRegistrationForm(ProfileRegistrationForm userForm) {
		
		Profile u = new Profile();

        BeanUtils.copyProperties(userForm, u);
        u.setPassword(bCrypt.encode(userForm.getPassword()));
        u.getProfileRoles().add(Role.USER);
        u.setActivationCode(UUID.randomUUID().toString());

        profileStorage.save(u);
        
        if (!StringUtils.isEmpty(u.getEmail())) {
        	String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    u.getNickname(),
                    u.getActivationCode()
            );            
            mailClient.sendMail("rfln.support@gmail.com", userForm.getEmail(), "Registration", message);
        }
        
        return true;
	}
	
	@Override
	public boolean activateProfile(String code) {
		
		Profile user = profileStorage.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setEnabled(true);

        profileStorage.save(user);

        return true;
    }
	
	public Profile getProfileByEmail(String email) {
		return profileStorage.findByEmailAndEnabledTrue(email);
	}

	@Override
	public List<ProfileJsonDTO> usersByUserAsJson(Long profileId, String divId) { // переименовать метод?
		List<Profile> profiles = null;
		List<ProfileJsonDTO> profilesJson = null;
		
		switch(divId) {
		case "friends":
			profiles = profileStorage.findFriends(profileId);
			break;
		case "followers":
			profiles = profileStorage.findFollowers(profileId);
			break;
		case "subscriptions":
			profiles = profileStorage.findSubscriptions(profileId);
			break;
		}
		
		if (profiles != null && profiles.size() > 0) {
			profilesJson = new ArrayList<>(profiles.size());
			for(Profile profile : profiles) {
				ProfileJsonDTO profileDTO = new ProfileJsonDTO();
				
				profileDTO.setNickname(profile.getNickname());
				profileDTO.setFullName(profile.getFullName());
				profileDTO.setLinkAvatar(profile.getLinkAvatar());
				
				profilesJson.add(profileDTO);
			}
		}
		
		return profilesJson;
	}

	@Override
	public Profile findById(Long profileId) {
		return (profileId != null) ? profileStorage.loadById(profileId) : null;
	}
	
}
