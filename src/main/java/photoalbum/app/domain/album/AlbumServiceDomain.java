package photoalbum.app.domain.album;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import photoalbum.app.data.AlbumStorage;
import photoalbum.app.domain.model.AccesLevel;
import photoalbum.app.domain.model.Album;

public class AlbumServiceDomain implements AlbumService {
	
	@Autowired
	AlbumStorage albumStorage;

	@Override
	public List<Album> getAlbumListByUser(Long profileId) {
		return albumStorage.findAlbumsByUser(profileId);
	}

	@Override
	public void insertAlbum(Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
		albumStorage.insert(profileId, albumName, numberOfPhotos, accesLevel);		
	}

	@Override
	public void updateAlbum(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
		albumStorage.update(id, profileId, albumName, numberOfPhotos, accesLevel);
	}

	@Override
	public void deleteAlbum(Long id) {
		albumStorage.delete(id);
	}
}
