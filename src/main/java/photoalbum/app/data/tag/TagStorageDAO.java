package photoalbum.app.data.tag;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import photoalbum.app.data.TagStorage;

public class TagStorageDAO implements TagStorage{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addTag(Long photo_id, String value) {
		String insertQuery = "INSERT INTO tags (photo_id, value) VALUES (?, ?)";
		Object[] data = new Object[] {photo_id, value};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Tags");
		}
	}

	@Override
	public void deleteTags(Long photo_id) {
		String updateQuery = "DELETE FROM tags WHERE photo_id = ?";
		Object[] data = new Object[] {photo_id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Tags");
		}
	}
}
