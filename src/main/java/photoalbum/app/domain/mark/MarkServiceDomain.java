package photoalbum.app.domain.mark;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import photoalbum.app.data.MarkStorage;
import photoalbum.app.domain.model.Mark;

public class MarkServiceDomain implements MarkService{
	
	@Autowired
	MarkStorage markStorage;

	@Override
	public List<Mark> getMarkListByPhoto(Long photo_id) {
		return markStorage.getMarksByPhoto(photo_id);
	}

	@Override
	public void addMark(Long photo_id, Long author_id, int value) {
		markStorage.addMark(photo_id, author_id, value);
	}

	@Override
	public void deleteMark(Long id) {
		markStorage.deleteMark(id);
	}
	
}