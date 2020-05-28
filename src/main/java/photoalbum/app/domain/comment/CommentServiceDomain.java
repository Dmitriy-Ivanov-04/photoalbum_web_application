package photoalbum.app.domain.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import photoalbum.app.data.CommentStorage;
import photoalbum.app.domain.model.Comment;

public class CommentServiceDomain implements CommentService {
	
	@Autowired
	CommentStorage commentStorage;

	@Override
	public List<Comment> getListComments(Long photoId) {
		return commentStorage.findAllComments(photoId);
	}

}
