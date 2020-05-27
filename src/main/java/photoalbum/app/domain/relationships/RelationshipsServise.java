package photoalbum.app.domain.relationships;

import java.util.List;

import photoalbum.app.domain.model.Relationships;


public interface RelationshipsServise {

	List<Relationships> getSubscribersList(Long target_id);
	List<Relationships> getSubscriptionsList(Long profile_id);
	List<Relationships> getFriendsList(Long profile_id);
	void sendInvite(Long profile_id, Long target_id);
	void acceptInvite(Long id);
	void deleteFriend(Long profile_id, Long target_id, Long id);
	void unsubscribe(Long id);
}
