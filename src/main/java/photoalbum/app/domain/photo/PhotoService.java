package photoalbum.app.domain.photo;

import java.io.IOException;
import java.util.List;

import photoalbum.app.domain.dto.PhotoJsonDTO;
import photoalbum.app.domain.model.Photo;

public interface PhotoService {
	
	void uploadPhoto(Long profileId, Long albumId, String description, String link);
	
	void deletePhoto(Long id);
	
	boolean copyPhotoIn(Long profileId, Long photoId, Long albumId)  throws IOException;
	
	//public List<PhotoJsonDTO> photosByUserAsJson(Long profileId);
	
	List<PhotoJsonDTO> photosByUserAsJson(List<Photo> photos);
	
	List<Photo> searchByParametrs(String query, int rating, String date);
	
	List<Photo> searchByTag(String tag);
}