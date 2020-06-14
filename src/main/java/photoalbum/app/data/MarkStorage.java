package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Mark;

public interface MarkStorage {
	List<Mark> getMarksByPhoto(Long photoId);
	void add(Long photoId, Long authorId, int value);
	void delete(Long id);
	Mark getMarkByPhotoAndUser(Long photoId, Long profileId);
	void change(Long photoId, Long authorId, int value);
	float getRatingByPhoto(Long photoId);
	void deleteByPhoto(Long photoId);
}