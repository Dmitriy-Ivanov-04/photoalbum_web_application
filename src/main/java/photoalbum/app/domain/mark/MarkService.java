package photoalbum.app.domain.mark;

import java.util.List;

import photoalbum.app.domain.dto.MarkJsonDTO;
import photoalbum.app.domain.model.Mark;

public interface MarkService {
	
	List<Mark> getMarkListByPhoto(Long photoId);
	
	void addMark(Long photoId, Long authorId, int value);
	
	public List<MarkJsonDTO> marksByPhotoAsJson(List<Mark> marks);
	
	void changeMark(Long photoId, Long authorId, int value);
	
	Mark findMarkByUserAndPhoto(Long photoId, Long profileId);
}

