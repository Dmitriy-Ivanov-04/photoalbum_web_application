package photoalbum.app.data.profile;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.model.Profile;

@Repository
public class ProfileStorageDAO implements ProfileStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Profile> findAll() {
		
		StringBuilder sql = new StringBuilder("SELECT * FROM profile");
		List<Profile> profile = jdbcTemplate.query(sql.toString(), new ProfileRowMapper());
		
		return profile;
	}

	@Override
	public int countByEmail(String email) {
		
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM profile WHERE email = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), new Object[] {email}, Integer.class);
	}

	@Override
	public void save(Profile profile) {
		if (profile.getId() == null || profile.getId() == 0) {
			this.insert(profile);
		} else {
			this.update(profile);
		}
		
	}

	@Override
	public Profile findByEmailAndEnabledTrue(String email) {

		StringBuilder sql = new StringBuilder("SELECT * FROM profile WHERE email = ? and enabled = true");
		
		Profile profile = jdbcTemplate.queryForObject(sql.toString(), new Object[] {email}, new ProfileRowMapper());

		return profile;
	}
	
	private void update(Profile profile) {
		String updateQuery = "UPDATE profile SET email = ?, firstName = ?, lastName = ?, nickname = ?, password = ?, enabled = ?, token = ?, roles = ?, linkAvatar = ?, linkBackground = ? WHERE id = ?";
		Object[] data = new Object[] {
			profile.getEmail(), profile.getFirstName(), profile.getLastName(), profile.getNickname(), profile.getPassword(), 
			profile.isEnabled(), profile.getToken(), String.join(",", profile.getRolesList()), profile.getLinkAvatar(), 
			profile.getLinkBackground(), profile.getId()
		};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Profile");
		}
	}

	private void insert(Profile profile) {
		String insertQuery = "INSERT INTO profile (email, firstName, lastName, nickname, password, enabled, token, roles) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] data = new Object[] {
			profile.getEmail(), profile.getFirstName(), profile.getLastName(), profile.getNickname(), profile.getPassword(), 
			profile.isEnabled(), profile.getToken(), String.join(",", profile.getRolesList())
		};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Profile");
		}
	}
	
	@Override
	public void addAvatar(Profile profile) {
		String updateQuery = "UPDATE profile SET linkAvatar = ? WHERE id = ?";
		Object[] data = new Object[] {
				profile.getLinkAvatar(), profile.getId()
		};
		int rowAffected = jdbcTemplate.update(updateQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during update avatar for Profile");
		}
		
	}
	
	@Override
	public void addBackground(Profile profile) {
		String updateQuery = "UPDATE profile SET linkBackground = ? WHERE id = ?";
		Object[] data = new Object[] {
				profile.getLinkBackground(), profile.getId()
		};
		int rowAffected = jdbcTemplate.update(updateQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during update background for Profile");
		}
		
	}

}
