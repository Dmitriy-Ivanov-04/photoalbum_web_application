package photoalbum.app.domain.tag;

public interface TagService {
	void addTags(Long photoId, String tags);
	void deleteTagsByPhoto(Long photoId);
}
