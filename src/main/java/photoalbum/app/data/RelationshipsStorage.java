package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Relationships;


public interface RelationshipsStorage {
	
	List<Relationships> findFollowers(Long targetId);
	List<Relationships> findSubscriptions(Long profileId);
	List<Relationships> findFriends(Long profileId);
	Relationships findRelationships(Long id);
	void sendInvite(Long profileId, Long targetId);
	void acceptInvite(Long id);
	void deleteFriend(Long profileId, Long targetId, Long id);
	void unsubscribe(Long id);
}
