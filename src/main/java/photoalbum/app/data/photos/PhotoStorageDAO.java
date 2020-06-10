package photoalbum.app.data.photos;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import photoalbum.app.data.PhotoStorage;
import photoalbum.app.data.relationships.RelationshipsRowMapper;
import photoalbum.app.domain.model.Photo;
@Repository
public class PhotoStorageDAO implements PhotoStorage{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Photo getPhotoById(Long id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE id = ? ORDER BY date");
		Photo photo = jdbcTemplate.queryForObject(sql.toString(), new Object[] {id}, new PhotoRowMapper());
		return photo;
	}
	
	@Override
	public List<Photo> getPhotosByUser(Long profileId) {
		System.out.println("id: " + profileId);
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE profile_id = ? ORDER BY date");
		List<Photo> photos = (List<Photo>) jdbcTemplate.query(sql.toString(), new Object[] {profileId}, new PhotoRowMapper());
		return photos;
	}

	@Override
	public List<Photo> getPhotosByAlbum(Long albumId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE album_id = ? ORDER BY date");
		List<Photo> photos = (List<Photo>) jdbcTemplate.query(sql.toString(), new Object[] {albumId}, new PhotoRowMapper());
		return photos;
	}

	@Override
	public List<Photo> getPhotosByRating(float rating) {
		StringBuilder sql = new StringBuilder("SELECT * FROM photos WHERE rating = ? ORDER BY date");
		List<Photo> photos = (List<Photo>) jdbcTemplate.query(sql.toString(), new Object[] {rating}, new PhotoRowMapper());
		return photos;
	}

	@Override
	public void upload(Long profileId, Long albumId, String description, String link) {
		String insertQuery = "INSERT INTO photos (profile_id, album_id, description, date, link_photo) VALUES (?, ?, ?, now(), ?)";
		Object[] data = new Object[] {profileId, albumId, description, link};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Photos");
		}
		
	}

	@Override
	public void delete(Long id) {
		String updateQuery = "DELETE FROM photos WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Photos");
		}
	}
	
	@Override
	public int countPublicationsByUser(Long profileId) {
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM photos WHERE profile_id = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), new Object[] {profileId}, Integer.class);
	}
}
