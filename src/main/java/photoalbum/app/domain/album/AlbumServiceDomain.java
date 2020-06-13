package photoalbum.app.domain.album;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import photoalbum.app.data.AlbumStorage;
import photoalbum.app.domain.dto.AlbumJsonDTO;
import photoalbum.app.domain.dto.CommentJsonDTO;
import photoalbum.app.domain.model.AccesLevel;
import photoalbum.app.domain.model.Album;
import photoalbum.app.domain.model.Comment;
@Service
public class AlbumServiceDomain implements AlbumService {
	
	@Autowired
	AlbumStorage albumStorage;

	@Override
	public void updateAlbum(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
		albumStorage.update(id, profileId, albumName, numberOfPhotos, accesLevel);
	}

	@Override
	public void deleteAlbum(Long id) {
		albumStorage.delete(id);
	}

	@Override
	public List<AlbumJsonDTO> albumsByUserAsJson(List<Album> albums) {
		List<AlbumJsonDTO> albumsJson = null;
		
		if(albums != null && albums.size() > 0) {
			albumsJson = new ArrayList<>(albums.size());
			for(Album album : albums) {
				AlbumJsonDTO albumDTO = new AlbumJsonDTO();
				
				albumDTO.setId(album.getId());
				albumDTO.setName(album.getAlbumName());
				
				albumsJson.add(albumDTO);
			}
		}
		return albumsJson;
	}
}
