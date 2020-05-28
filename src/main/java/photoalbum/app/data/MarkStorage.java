package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Mark;

public interface MarkStorage {
	List<Mark> getMarksByPhoto(Long photo_id);
	void add(Long photoId, Long authorId, int value);
	void delete(Long id);
}