package photoalbum.app.domain.tag;

import photoalbum.app.data.TagStorage;

public class TagServiceDomain implements TagService{
	
	TagStorage tagStorage;
	
	@Override
	public void addTags(Long photo_id, String value) {
		value.replace(",", "");
		value.replace(".", "");
		value.replace(";", "");
		String[] tags = value.split(" ");
		for(int i = 0; i < tags.length; i++)
			tagStorage.addTag(photo_id, tags[i]);
	}

	@Override
	public void deleteTagsByPhoto(Long photo_id) {
		tagStorage.deleteTags(photo_id);
	}
	
}
