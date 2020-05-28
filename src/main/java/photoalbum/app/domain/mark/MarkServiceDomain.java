package photoalbum.app.domain.mark;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import photoalbum.app.data.MarkStorage;
import photoalbum.app.domain.model.Mark;

public class MarkServiceDomain implements MarkService{
	
	@Autowired
	MarkStorage markStorage;

	@Override
	public List<Mark> getMarkListByPhoto(Long photoId) {
		return markStorage.getMarksByPhoto(photoId);
	}

	@Override
	public void addMark(Long photoId, Long authorId, int value) {
		markStorage.add(photoId, authorId, value);
	}

	@Override
	public void deleteMark(Long id) {
		markStorage.delete(id);
	}
	
}