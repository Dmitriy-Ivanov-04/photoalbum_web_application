package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Photo;

public interface PhotoStorage {
	Photo getPhotoById(Long id);
	List<Photo> getPhotosByUser(Long profile_id);
	List<Photo> getPhotosByAlbum(Long album_id);
	//List<Photo> getPhotosByDate(Date date);
	List<Photo> getPhotosByRating(float rating);
	//List<Photo> getPhotosByTag(String id);
	void uploadPhoto(Long profile_id, Long album_id, String description, String link_photo);
	void deletePhoto(Long id);
	//void copyPhoto();
}
