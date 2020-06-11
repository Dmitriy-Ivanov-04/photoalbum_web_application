package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Tag;

public interface TagStorage {
	void add(Long photoId, String value);
	void delete(Long photoId);
	List<Tag> getTagsByPhoto(Long photoId);
}
