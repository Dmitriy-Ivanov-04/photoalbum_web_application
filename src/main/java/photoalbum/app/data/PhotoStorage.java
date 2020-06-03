package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Photo;

public interface PhotoStorage {
	Photo getPhotoById(Long id);
	List<Photo> getPhotosByUser(Long profileId);
	List<Photo> getPhotosByAlbum(Long albumId);
	//List<Photo> getPhotosByDate(Date date);
	List<Photo> getPhotosByRating(float rating);
	//List<Photo> getPhotosByTag(String id);
	void upload(Long profileId, Long albumId, String description, String link);
	void delete(Long id);
	//void copyPhoto();
	int countPublicationsByUser(Long profileId);
}
