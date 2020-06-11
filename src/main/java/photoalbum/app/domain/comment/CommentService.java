package photoalbum.app.domain.comment;

import java.util.List;

import photoalbum.app.domain.dto.CommentJsonDTO;
import photoalbum.app.domain.model.Comment;

public interface CommentService {
	
	List<Comment> getListComments(Long photoId);
	public List<CommentJsonDTO> commentsByPhotoAsJson(List<Comment> comments);
}
