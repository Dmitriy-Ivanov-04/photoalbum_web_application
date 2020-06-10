package photoalbum.app.domain.photo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import photoalbum.app.data.PhotoStorage;
import photoalbum.app.domain.dto.PhotoJsonDTO;
import photoalbum.app.domain.model.Photo;

@Service
public class PhotoServiceDomain implements PhotoService{
	
	PhotoStorage photoStorage;

	@Override
	public List<Photo> getPhotoListByUser(Long profileId) {
		photoStorage.getPhotosByUser(profileId);
		return null;
	}

	@Override
	public List<Photo> getPhotoListByAlbum(Long albumId) {
		photoStorage.getPhotosByAlbum(albumId);
		return null;
	}

	@Override
	public List<Photo> getPhotoListByRating(float rating) {
		photoStorage.getPhotosByRating(rating);
		return null;
	}

	@Override
	public void uploadPhoto(Long profileId, Long albumId, String description, String link) {
		photoStorage.upload(profileId, albumId, description, link);		
	}

	@Override
	public void deletePhoto(Long id) {
		photoStorage.delete(id);	
	}

	@Override
	public void copyPhoto(Long photoId, Long profileId, Long albumId) {
		photoStorage.upload(profileId, albumId, photoStorage.getPhotoById(photoId).getDescription(), photoStorage.getPhotoById(photoId).getLink_photo());		
	}

	@Override
	public List<PhotoJsonDTO> photosByUserAsJson(List<Photo> photos1) {
	//public List<PhotoJsonDTO> photosByUserAsJson(Long profileId) {
		//List<Photo> photos = photoStorage.getPhotosByUser(profileId);
		List<Photo> photos = photos1;
		List<PhotoJsonDTO> photosJson = null;
		
		if(photos != null && photos.size() > 0) {
			photosJson = new ArrayList<>(photos.size());
			for(Photo photo : photos) {
				PhotoJsonDTO photoDTO = new PhotoJsonDTO();
				
				photoDTO.setLink(photo.getLink_photo());
				
				photosJson.add(photoDTO);
			}
		}
		return photosJson;
	}

}
