package photoalbum.app.domain.mark;

import java.util.List;

import photoalbum.app.domain.model.Mark;

public interface MarkService {
	List<Mark> getMarkListByPhoto(Long photo_id);
	void addMark(Long photo_id, Long author_id, int value);
	void deleteMark(Long id);
}
