package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.AccesLevel;
import photoalbum.app.domain.model.Album;

public interface AlbumStorage {
	
	List<Album> findAlbumsByUser(Long profile_id);
	void insert(Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel);
	void update(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel);
	void delete(Long id);
}
