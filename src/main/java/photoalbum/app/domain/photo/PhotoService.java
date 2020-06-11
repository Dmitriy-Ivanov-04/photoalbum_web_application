package photoalbum.app.domain.photo;

import java.util.List;

import photoalbum.app.domain.dto.PhotoJsonDTO;
import photoalbum.app.domain.model.Photo;

public interface PhotoService {
	List<Photo> getPhotoListByUser(Long profileId);
	List<Photo> getPhotoListByAlbum(Long albumId);
	List<Photo> getPhotoListByRating(float rating);
	void uploadPhoto(Long profileId, Long albumId, String description);
	void deletePhoto(Long id);
	void copyPhoto(Long photoId, Long profileId, Long albumId);
	//public List<PhotoJsonDTO> photosByUserAsJson(Long profileId);
	public List<PhotoJsonDTO> photosByUserAsJson(List<Photo> photos);
}