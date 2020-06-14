package photoalbum.app.domain.photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import photoalbum.app.data.AlbumStorage;
import photoalbum.app.data.CommentStorage;
import photoalbum.app.data.MarkStorage;
import photoalbum.app.data.PhotoStorage;
import photoalbum.app.domain.dto.PhotoJsonDTO;
import photoalbum.app.domain.model.Photo;
import photoalbum.app.domain.tag.TagService;
import photoalbum.app.web.form.UploadForm;

@Service
public class PhotoServiceDomain implements PhotoService{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	PhotoStorage photoStorage;
	
	@Autowired
	AlbumStorage albumStorage;
	
	@Autowired
	TagService tagService;
	
	@Autowired
	MarkStorage markStorage;
	
	@Autowired
	CommentStorage commentStorage;

	@Override
	public void uploadPhoto(Long profileId, Long albumId, String description, String link) {
		photoStorage.upload(profileId, albumId, description, link);		
	}

	@Override
	public void deletePhoto(Long photoId) {
		photoStorage.delete(photoId);
		commentStorage.deleteByPhoto(photoId);
		markStorage.deleteByPhoto(photoId);
		tagService.deleteTagsByPhoto(photoId);
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
				photoDTO.setAccesLevel(photoStorage.getAccesLevel(photo.getId()));
				
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
	
	public boolean savePhoto(MultipartFile multipartFile, Long profileId, UploadForm uploadForm) {
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
			multipartFile.transferTo(dest);
			uploadPhoto(profileId, albumStorage.getAlbumByNameAndUser(uploadForm.getAlbumName(), profileId).getId(), uploadForm.getDescription(), orgName);
			tagService.addTags(photoStorage.getPhotoByLink(orgName).getId(), uploadForm.getTags());


		} catch (IllegalStateException e) {
			logger.severe(e.getMessage());
			result = false;
		} catch (IOException e) {
			logger.severe(e.getMessage());
			result = false;
		}

		return result;
	}
	
	private static void copyFile(String src, String dest) {
		Path result = null;
		
		try {
			result =  Files.copy(Paths.get(src), Paths.get(dest));
		} catch (IOException e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}
		
		if(result != null) {
			System.out.println("File moved successfully.");
		} else {
			System.out.println("File movement failed.");
		} 
		
	}
	
	public boolean copyPhoto(Long profileId, Long photoId) throws IOException {
		boolean result = true;
		String randomName = UUID.randomUUID().toString();
		String filePath = photoDirPath + File.separator + profileId + File.separator;
		String photoPath = photoDirPath + File.separator + photoStorage.getProfileIdByPhoto(photoId) + File.separator + photoStorage.getPhotoById(photoId).getLink_photo();
		
		if (!(new File(filePath).exists())) {
			new File(filePath).mkdirs();
		}

		try {
			
			String orgName = randomName + photoStorage.getPhotoById(photoId).getLink_photo().substring(36);
			String fullFilePath = filePath + orgName;

			copyFile(photoPath, fullFilePath);
			
			Long copyAlbumId;
			try{
				copyAlbumId = albumStorage.getAlbumByNameAndUser("Copied photos", profileId).getId();
			} catch (EmptyResultDataAccessException e){
				albumStorage.insert(profileId, "Copied photos", 2);
				copyAlbumId = albumStorage.getAlbumByNameAndUser("Copied photos", profileId).getId();
			}
			uploadPhoto(profileId, copyAlbumId, photoStorage.getPhotoById(photoId).getDescription(), orgName);

		} catch (IllegalStateException e) {
			logger.severe(e.getMessage());
			result = false;
		}

		return result;
	}

	@Override
	public List<Photo> searchByParametrs(String query, int rating, String date) {
		List<Photo> photos = photoStorage.getPhotosByParametrs(query, date);
		if(rating > 0) {
			for(int i = 0; i < photos.size(); i++) {
				if(markStorage.getRatingByPhoto(photos.get(i).getId()) < rating) {
					photos.remove(i);
					i--;
				}
			}
		}
		for(int i = 0; i < photos.size(); i++) {
			 if(photoStorage.getAccesLevel(photos.get(i).getId()) != 0) {
				 photos.remove(i);
					i--;
			 }
		}
		return photos;
	}

	@Override
	public List<Photo> searchByTag(String tag) {
		List<Photo> photos = photoStorage.getPhotosByTag(tag);
		for(int i = 0; i < photos.size(); i++) {
			 if(photoStorage.getAccesLevel(photos.get(i).getId()) != 0) {
				 photos.remove(i);
					i--;
			 }
		}
		return photos;
	}

}
