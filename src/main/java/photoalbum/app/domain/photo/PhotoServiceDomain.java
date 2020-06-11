package photoalbum.app.domain.photo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import photoalbum.app.data.PhotoStorage;
import photoalbum.app.domain.dto.PhotoJsonDTO;
import photoalbum.app.domain.model.Photo;

@Service
public class PhotoServiceDomain implements PhotoService{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
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
		//photoStorage.upload(profileId, albumId, photoStorage.getPhotoById(photoId).getDescription());		
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
				
				photoDTO.setId(photo.getId());
				photoDTO.setAlbum_id(photo.getAlbum_id());
				photoDTO.setDescription(photo.getDescription());
				photoDTO.setDate(photo.getDate());
				photoDTO.setLink(photo.getLink_photo());
				
				photosJson.add(photoDTO);
			}
		}
		return photosJson;
	}
	
	@Value("${project.manager.photo.dir.path}")
	private String photoDirPath;

	public FileSystemResource getImage(Long photoId) {
		String photoPath = photoDirPath + File.separator + photoStorage.getProfileIdByPhoto(photoId) + File.separator + photoStorage.getPhotoById(photoId).getLink_photo();

		File f = new File(photoPath);
		return new FileSystemResource(f);

	}
	
	public boolean savePhoto(MultipartFile multipartFile, Long profileId) {
		boolean result = true;
		String randomName = UUID.randomUUID().toString();
		String filePath = photoDirPath + File.separator + profileId + File.separator;

		if (!(new File(filePath).exists())) {
			new File(filePath).mkdirs();
		}

		try {
			
			String orgName = randomName + multipartFile.getOriginalFilename();
			String fullFilePath = filePath + orgName;

			File dest = new File(fullFilePath);
			uploadPhoto(profileId, (long) 1, "description", orgName);
			multipartFile.transferTo(dest);
			


		} catch (IllegalStateException e) {
			logger.severe(e.getMessage());
			result = false;
		} catch (IOException e) {
			logger.severe(e.getMessage());
			result = false;
		}

		return result;
	}

}
