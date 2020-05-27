package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Profile;

public interface ProfileStorage {
	
	List<Profile> findAll();
	
	int countByEmail(String email);
	
	void save(Profile u);
	
	Profile findByEmailAndEnabledTrue(String email);

}
