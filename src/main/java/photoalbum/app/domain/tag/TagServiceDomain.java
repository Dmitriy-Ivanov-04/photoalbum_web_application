package photoalbum.app.domain.tag;

import org.springframework.stereotype.Service;
import photoalbum.app.data.TagStorage;
@Service
public class TagServiceDomain implements TagService{
	
	TagStorage tagStorage;
	
	@Override
	public void addTags(Long photoId, String value) {
		value.replace(",", "");
		value.replace(".", "");
		value.replace(";", "");
		String[] tags = value.split(" ");
		for(int i = 0; i < tags.length; i++)
			tagStorage.add(photoId, tags[i]);
	}

	@Override
	public void deleteTagsByPhoto(Long photoId) {
		tagStorage.delete(photoId);
	}
	
}
