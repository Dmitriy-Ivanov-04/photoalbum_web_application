package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Comment;

public interface CommentStorage {
	
	List<Comment> findAllComments(Long profileId);

}
