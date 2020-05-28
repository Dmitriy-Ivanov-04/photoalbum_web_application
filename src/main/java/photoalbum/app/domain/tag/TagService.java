package photoalbum.app.domain.tag;

public interface TagService {
	void addTags(Long photo_id, String tags);
	void deleteTagsByPhoto(Long photo_id);
}
