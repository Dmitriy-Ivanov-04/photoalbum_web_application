package photoalbum.app.domain.mark;

import java.util.List;

import photoalbum.app.domain.model.Mark;

public interface MarkService {
	List<Mark> getMarkListByPhoto(Long photoId);
	void addMark(Long photoId, Long authorId, int value);
	void deleteMark(Long id);
}
