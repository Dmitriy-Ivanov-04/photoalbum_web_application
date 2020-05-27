package photoalbum.app.domain.photo;

import java.util.List;

import photoalbum.app.domain.model.Photo;

public interface PhotoService {
	List<Photo> getPhotoListByUser(Long profile_id);
	List<Photo> getPhotoListByAlbum(Long album_id);
	List<Photo> getPhotoListByRating(float rating);
	void uploadPhoto(Long profile_id, Long album_id, String description, String link_photo);
	void deletePhoto(Long id);
	void copyPhoto(Long photo_id, Long profile_id, Long album_id);
}