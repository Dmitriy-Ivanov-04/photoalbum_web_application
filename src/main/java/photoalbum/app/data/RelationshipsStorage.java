package photoalbum.app.data;

import java.util.List;

import photoalbum.app.domain.model.Relationships;


public interface RelationshipsStorage {
	
	List<Relationships> findSubscribers(Long target_id);
	List<Relationships> findSubscriptions(Long profile_id);
	List<Relationships> findFriends(Long profile_id);
	Relationships findRelationships(Long id);
	void sendInvite(Long profile_id, Long target_id);
	void acceptInvite(Long id);
	void deleteFriend(Long profile_id, Long target_id, Long id);
	void unsubscribe(Long id);
}
