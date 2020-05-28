package photoalbum.app.data;

public interface TagStorage {
	void add(Long photoId, String value);
	void delete(Long photoId);
}
