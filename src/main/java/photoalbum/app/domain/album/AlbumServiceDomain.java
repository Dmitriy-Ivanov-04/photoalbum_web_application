package photoalbum.app.domain.album;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import photoalbum.app.data.AlbumStorage;
import photoalbum.app.domain.model.Album;

public class AlbumServiceDomain implements AlbumService {
	
	@Autowired
	AlbumStorage albumStorage;

	@Override
	public List<Album> getListAlbums() {
		return albumStorage.findAlbumsByUser();
	}

}
