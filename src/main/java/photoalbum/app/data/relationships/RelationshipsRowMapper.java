package photoalbum.app.data.relationships;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import photoalbum.app.domain.model.Relationships;

public class RelationshipsRowMapper implements RowMapper<Relationships> {
	@Override
	public Relationships mapRow(ResultSet rs, int rowNum) throws SQLException {
		Relationships relationships = new Relationships();
		
		relationships.setId(rs.getLong("id"));
		relationships.setProfile_id(rs.getLong("profile_id"));
		relationships.setTarget_id(rs.getLong("target_id"));
		relationships.setStatus(rs.getString("status"));

		return relationships;
	}
}
