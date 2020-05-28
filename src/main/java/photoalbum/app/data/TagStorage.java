package photoalbum.app.data;

public interface TagStorage {
	void addTag(Long photo_id, String value);
	void deleteTags(Long photo_id);
}
