package photoalbum.app.data.comment;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import photoalbum.app.data.CommentStorage;
import photoalbum.app.data.photos.PhotoRowMapper;
import photoalbum.app.domain.model.Comment;
import photoalbum.app.domain.model.Photo;

public class CommentStorageDAO implements CommentStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Comment> findAllComments(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM comments WHERE photo_id = ? ORDER BY date");
		List<Comment> comments = (List<Comment>) jdbcTemplate.queryForObject(sql.toString(), new Object[] {profileId}, new PhotoRowMapper());
		
		return comments;
	}

}
