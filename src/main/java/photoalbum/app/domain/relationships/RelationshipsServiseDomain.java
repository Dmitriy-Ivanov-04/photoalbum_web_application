package photoalbum.app.domain.relationships;

import java.util.List;

import photoalbum.app.data.RelationshipsStorage;
import photoalbum.app.domain.model.Relationships;

public class RelationshipsServiseDomain implements RelationshipsServise{
	
	RelationshipsStorage relationshipsStorage;
	
	public List<Relationships> getSubscribersList(Long target_id){
		return relationshipsStorage.findSubscribers(target_id);
	}
	
	public List<Relationships> getSubscriptionsList(Long profile_id){
		return relationshipsStorage.findSubscriptions(profile_id);
	}
	
	public List<Relationships> getFriendsList(Long profile_id){
		return relationshipsStorage.findFriends(profile_id);
	}
	
	public void sendInvite(Long profile_id, Long target_id) {
		relationshipsStorage.sendInvite(profile_id, target_id);
	}
	
	public void acceptInvite(Long id) {
		relationshipsStorage.acceptInvite(id);
	}
	
	public void deleteFriend(Long profile_id, Long target_id, Long id) {
		Relationships relationships = relationshipsStorage.findRelationships(id);
		if(relationships.getProfile_id() == profile_id)
			relationshipsStorage.deleteFriend(profile_id, target_id, id);
		if(relationships.getProfile_id() == target_id)
			relationshipsStorage.deleteFriend(target_id, profile_id, id);
	}
	
	public void unsubscribe(Long id) {
		relationshipsStorage.unsubscribe(id);
	}
}
