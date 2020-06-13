package photoalbum.app.domain.album;

import java.util.List;

import photoalbum.app.domain.dto.AlbumJsonDTO;
import photoalbum.app.domain.model.AccesLevel;
import photoalbum.app.domain.model.Album;

public interface AlbumService {

	void updateAlbum(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel);
	void deleteAlbum(Long id);
	public List<AlbumJsonDTO> albumsByUserAsJson(List<Album> albums);
}
