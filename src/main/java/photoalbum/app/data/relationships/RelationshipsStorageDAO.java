package photoalbum.app.data.relationships;
import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import photoalbum.app.data.RelationshipsStorage;
import photoalbum.app.domain.model.Relationships;
@Repository
public class RelationshipsStorageDAO implements RelationshipsStorage{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Relationships> findSubscribers(Long targetId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE target_id = ? AND status = subscriber");
		List<Relationships> relationships = jdbcTemplate.query(sql.toString(), new Object[] {targetId}, new RelationshipsRowMapper());
		return relationships;
	}
	
	public List<Relationships> findSubscriptions(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE profile_id = ? AND status = subscriber");
		List<Relationships> relationships = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new RelationshipsRowMapper());
		return relationships;
	}
	
	public List<Relationships> findFriends(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE target_id = ? AND status = frined");
		List<Relationships> relationships = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new RelationshipsRowMapper());
		sql = new StringBuilder("SELECT * FROM relationships WHERE profile_id = ? AND status = frined");
		relationships = jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new RelationshipsRowMapper());
		return relationships;
	}
	
	public Relationships findRelationships(Long id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM relationships WHERE id = ?");
		Relationships relationships = jdbcTemplate.queryForObject(sql.toString(), new Object[] {id}, new RelationshipsRowMapper());
		return relationships;
	}
	
	public void sendInvite(Long profileId, Long targetId) {
		String insertQuery = "INSERT INTO relationships (profile_id, target_id, status) VALUES (?, ?, subscriber)";
		Object[] data = new Object[] {profileId, targetId};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Relationships");
		}
	}
	
	public void acceptInvite(Long id) {
		String updateQuery = "UPDATE relationships SET status = friend WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Relationships");
		}
	}
	
	public void deleteFriend(Long profileId, Long targetId, Long id) {
		String updateQuery = "UPDATE relationships SET profile_id = ?, target_id = ?, status = subscriber WHERE id = ?";
		Object[] data = new Object[] {profileId, targetId, id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Relationships");
		}
	}

	public void unsubscribe(Long id) {
		String updateQuery = "DELETE FROM relationships WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Relationships");
		}
	}
}
