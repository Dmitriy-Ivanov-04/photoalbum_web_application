package photoalbum.app.domain.photo;

import java.util.List;

import photoalbum.app.domain.model.Photo;

public interface PhotoService {
	List<Photo> getPhotoListByUser(Long profileId);
	List<Photo> getPhotoListByAlbum(Long albumId);
	List<Photo> getPhotoListByRating(float rating);
	void uploadPhoto(Long profileId, Long albumId, String description, String link);
	void deletePhoto(Long id);
	void copyPhoto(Long photoId, Long profileId, Long albumId);
}