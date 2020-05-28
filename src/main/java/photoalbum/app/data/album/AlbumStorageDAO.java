package photoalbum.app.data.album;

import java.util.List;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import photoalbum.app.data.AlbumStorage;
import photoalbum.app.domain.model.Album;

public class AlbumStorageDAO implements AlbumStorage {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Album> findAlbumsByUser() {
		StringBuilder sql = new StringBuilder("SELECT * FROM albums");
		List<Album> album = jdbcTemplate.query(sql.toString(), new AlbumRowMapper());
		
		return album;
	}

	@Override
	public void save(Album album) {
		if (album.getId() == null || album.getId() == 0) {
			this.insert(album);
		} else {
			this.update(album);
		}
		
	}
	
	private void update(Album album) {
		String updateQuery = "UPDATE albums SET profile_id = ?, album_name = ?, number_of_photos = ?, acces_level = ? WHERE id = ?";
		Object[] data = new Object[] {
				album.getProfileId(), album.getAlbumName(), album.getNumberOfPhotos(), album.getAccesLevel(), album.getId()
		};
		int rowAffected = jdbcTemplate.update(updateQuery, data);

		if (rowAffected == 0) {
			logger.error("Error during update record for Album");
		}
	}
	
	private void insert(Album album) {
		String insertQuery = "INSERT INTO albums (profile_id, album_name, number_of_photos, acces_level) VALUES (?, ?, ?, ?)";
		Object[] data = new Object[] {
				album.getProfileId(), album.getAlbumName(), album.getNumberOfPhotos(), album.getAccesLevel()
		};
		int rowAffected = jdbcTemplate.update(insertQuery, data);
		
		if (rowAffected == 0) {
			logger.error("Error during insert record for Album");
		}
	}

}
