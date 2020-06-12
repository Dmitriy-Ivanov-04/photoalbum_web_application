package photoalbum.app.data.album;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import photoalbum.app.data.AlbumStorage;
import photoalbum.app.data.relationships.RelationshipsRowMapper;
import photoalbum.app.domain.model.AccesLevel;
import photoalbum.app.domain.model.Album;
import photoalbum.app.domain.model.Relationships;
@Repository
public class AlbumStorageDAO implements AlbumStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Album> findAlbumsByUser(Long profile_id, int accesLevel) {
		StringBuilder sql = new StringBuilder("SELECT * FROM albums WHERE profile_id = ? AND acces_level <= ?");
		List<Album> album = jdbcTemplate.query(sql.toString(), new Object[] {profile_id, accesLevel}, new AlbumRowMapper());
		return album;
	}
	
	public void insert(Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
		String insertQuery = "INSERT INTO albums (profile_id, album_name, number_of_photos, acces_level) VALUES (?, ?, ?, ?)";
		Object[] data = new Object[] {profileId, albumName, numberOfPhotos, accesLevel};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		if (rowAffected == 0) {
			logger.error("Error during update record for Album");
		}
	}
	
	public void update(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
		String updateQuery = "UPDATE albums SET profile_id = ?, album_name = ?, number_of_photos = ?, acces_level = ? WHERE id = ?";
		Object[] data = new Object[] {profileId, albumName, numberOfPhotos, accesLevel, id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during update record for Album");
		}
	}

	@Override
	public void delete(Long id) {
		String updateQuery = "DELETE FROM albums WHERE id = ?";
		Object[] data = new Object[] {id};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during delete record for Album");
		}
	}
}
