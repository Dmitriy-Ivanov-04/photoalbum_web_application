package photoalbum.app.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.model.Profile;

public class ProfileDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ProfileStorage profileStorage;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Profile profile = profileStorage.findByEmailAndEnabledTrue(username);
		return new ProfileDetailsImpl(profile);
	}

}
