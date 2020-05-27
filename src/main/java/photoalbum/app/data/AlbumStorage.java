package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Album;

public interface AlbumStorage {
	
	List<Album> findAlbumsByUser();
		
	void save(Album a);

}
