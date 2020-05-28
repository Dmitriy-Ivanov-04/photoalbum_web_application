package photoalbum.app.data.album;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import photoalbum.app.domain.model.AccesLevel;
import photoalbum.app.domain.model.Album;

public class AlbumRowMapper implements RowMapper<Album> {
	

	@Override
	public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
		Album album = new Album();
		
		album.setId(rs.getLong("id"));
		album.setProfileId(rs.getLong("profile_id"));
		album.setAlbumName(rs.getString("album_name"));
		album.setNumberOfPhotos(rs.getLong("number_of_photos"));
		album.setAccesLevel(rs.getString("acces_level"));
        
        return album;

	}

}
