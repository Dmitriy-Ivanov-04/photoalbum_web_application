package photoalbum.app.domain.tag;

import java.util.List;

import photoalbum.app.domain.dto.TagJsonDTO;
import photoalbum.app.domain.model.Tag;

public interface TagService {

	void addTags(Long photoId, String tags);

	void deleteTagsByPhoto(Long photoId);

	public List<TagJsonDTO> tagsByPhotoAsJson(List<Tag> tags);

}
