package photoalbum.app.domain.album;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import photoalbum.app.data.AlbumStorage;
import photoalbum.app.data.PhotoStorage;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.dto.AlbumJsonDTO;
import photoalbum.app.domain.mail.MailClient;
import photoalbum.app.domain.model.AccesLevel;
import photoalbum.app.domain.model.Album;
import photoalbum.app.domain.model.Photo;
import photoalbum.app.domain.photo.PhotoService;

@Service
public class AlbumServiceDomain implements AlbumService {
	
	@Autowired
	AlbumStorage albumStorage;
	
	@Autowired
	PhotoStorage photoStorage;
	
	@Autowired
	PhotoService photoService;
	
	@Autowired
	MailClient mailClient;
	
	@Autowired
	ProfileStorage profileStorage;

	@Override
	public void updateAlbum(Long id, Long profileId, String albumName, int numberOfPhotos, AccesLevel accesLevel) {
		albumStorage.update(id, profileId, albumName, numberOfPhotos, accesLevel);
	}

	@Override
	public void deleteAlbum(Long profileId, Long id) {
		String emailProfile = profileStorage.findEmailById(profileId);
		String nicknameAuthor = profileStorage.findNicknameByEmail(emailProfile);
		String nameAlbum = albumStorage.getAlbumNameById(id);
		
		List<Photo> photos = photoStorage.getPhotosByAlbum(id);
		for(int i = 0; i < photos.size(); i++) {
			photoService.deletePhoto(photos.get(i).getId());
		}
		albumStorage.delete(id);
		
		if (!StringUtils.isEmpty(emailProfile)) {
        	String message = String.format(
                    "Hello, %s! \n" +
                            "Removed album called: %s.",
                    nicknameAuthor,
                    nameAlbum
            );       
        	mailClient.sendMail("rfln.support@gmail.com", emailProfile, "Delete album", message);
        }
		
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
