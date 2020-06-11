package photoalbum.app.data;

import java.sql.Date;
import java.util.List;

import photoalbum.app.domain.model.Comment;

public interface CommentStorage {
	
	List<Comment> findAllComments(Long profileId);
	void add(Long photoId, Long authorId, String text);
	List<Comment> getCommentsByPhoto(Long photoId);
}
