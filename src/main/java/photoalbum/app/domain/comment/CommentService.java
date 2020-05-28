package photoalbum.app.domain.comment;

import java.util.List;

import photoalbum.app.domain.model.Comment;

public interface CommentService {
	
	List<Comment> getListComments(Long photoId);

}
