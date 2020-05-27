package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Mark;

public interface MarkStorage {
	List<Mark> getMarksByPhoto(Long photo_id);
	void addMark(Long photo_id, Long author_id, int value);
	void deleteMark(Long id);
}