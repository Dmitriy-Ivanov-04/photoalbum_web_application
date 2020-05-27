package photoalbum.app.domain.photo;

import java.util.List;

import photoalbum.app.data.PhotoStorage;
import photoalbum.app.domain.model.Photo;

public class PhotoServiceDomain implements PhotoService{
	
	PhotoStorage photoStorage;

	@Override
	public List<Photo> getPhotoListByUser(Long profile_id) {
		photoStorage.getPhotosByUser(profile_id);
		return null;
	}

	@Override
	public List<Photo> getPhotoListByAlbum(Long album_id) {
		photoStorage.getPhotosByAlbum(album_id);
		return null;
	}

	@Override
	public List<Photo> getPhotoListByRating(float rating) {
		photoStorage.getPhotosByRating(rating);
		return null;
	}

	@Override
	public void uploadPhoto(Long profile_id, Long album_id, String description, String link_photo) {
		photoStorage.uploadPhoto(profile_id, album_id, description, link_photo);		
	}

	@Override
	public void deletePhoto(Long id) {
		photoStorage.deletePhoto(id);	
	}

	@Override
	public void copyPhoto(Long photo_id, Long profile_id, Long album_id) {
		photoStorage.uploadPhoto(profile_id, album_id, photoStorage.getPhotoById(photo_id).getDescription(), photoStorage.getPhotoById(photo_id).getLink_photo());		
	}

}
