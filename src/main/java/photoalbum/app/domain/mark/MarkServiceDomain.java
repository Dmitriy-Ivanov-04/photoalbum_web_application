package photoalbum.app.domain.mark;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import photoalbum.app.data.MarkStorage;
import photoalbum.app.domain.dto.CommentJsonDTO;
import photoalbum.app.domain.dto.MarkJsonDTO;
import photoalbum.app.domain.model.Comment;
import photoalbum.app.domain.model.Mark;
@Service
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

	@Override
	public List<MarkJsonDTO> marksByPhotoAsJson(List<Mark> marks1) {
		List<Mark> marks = marks1;
		List<MarkJsonDTO> marksJson = null;
		
		if(marks != null && marks.size() > 0) {
			marksJson = new ArrayList<>(marks.size());
			for(Mark mark : marks) {
				MarkJsonDTO markDTO = new MarkJsonDTO();
				
				markDTO.setValue(mark.getValue());
				
				marksJson.add(markDTO);
			}
		}
		return marksJson;
	}
	
}