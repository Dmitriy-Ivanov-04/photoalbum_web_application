package photoalbum.app.data.photoalbum.app.data.mark;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import photoalbum.app.data.MarkStorage;
import photoalbum.app.data.photos.PhotoRowMapper;
import photoalbum.app.domain.model.Mark;
import photoalbum.app.domain.model.Photo;

public class MarkStorageDAO implements MarkStorage{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Mark> getMarksByPhoto(Long photo_id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM marks WHERE photo_id = ?");
		List<Mark> marks = (List<Mark>) jdbcTemplate.queryForObject(sql.toString(), new Object[] {photo_id}, new MarkRowMapper());
		return marks;
	}

	@Override
	public void addMark(Long photo_id, Long author_id, int value) {
		String insertQuery = "INSERT INTO marks (photo_id, author_id, value) VALUES (?, ?, ?)";
		Object[] data = new Object[] {photo_id, author_id, value};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Marks");
		}
	}

	@Override
	public void deleteMark(Long id) {
		String updateQuery = "DELETE FROM marks WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Marks");
		}
	}
	
}
