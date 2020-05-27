package photoalbum.app.data.photos;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import photoalbum.app.domain.model.Photo;

public class PhotoRowMapper  implements RowMapper<Photo>{
	@Override
	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo photo = new Photo();
		
		photo.setId(rs.getLong("id"));
		photo.setProfile_id(rs.getLong("profile_id"));
		photo.setAlbum_id(rs.getLong("album_id"));
		photo.setRating(rs.getFloat("rating"));
		photo.setDescription(rs.getString("description"));
		photo.setDate(rs.getDate("date"));
		photo.setMark_counter(rs.getInt("mark_counter"));
		photo.setLink_photo(rs.getString("link_photo"));

		return photo;
	}
}