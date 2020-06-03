package photoalbum.app.domain.relationships;

import java.util.List;

import org.springframework.stereotype.Service;
import photoalbum.app.data.RelationshipsStorage;
import photoalbum.app.domain.model.Relationships;
@Service
public class RelationshipsServiceDomain implements RelationshipsServise{
	
	RelationshipsStorage relationshipsStorage;
	
	public List<Relationships> getSubscribersList(Long targetId){
		return relationshipsStorage.findFollowers(targetId);
	}
	
	public List<Relationships> getSubscriptionsList(Long profileId){
		return relationshipsStorage.findSubscriptions(profileId);
	}
	
	public List<Relationships> getFriendsList(Long profileId){
		return relationshipsStorage.findFriends(profileId);
	}
	
	public void sendInvite(Long profileId, Long targetId) {
		relationshipsStorage.sendInvite(profileId, targetId);
	}
	
	public void acceptInvite(Long id) {
		relationshipsStorage.acceptInvite(id);
	}
	
	public void deleteFriend(Long profileId, Long targetId, Long id) {
		Relationships relationships = relationshipsStorage.findRelationships(id);
		if(relationships.getProfile_id() == profileId)
			relationshipsStorage.deleteFriend(profileId, targetId, id);
		if(relationships.getProfile_id() == targetId)
			relationshipsStorage.deleteFriend(targetId, profileId, id);
	}
	
	public void unsubscribe(Long id) {
		relationshipsStorage.unsubscribe(id);
	}
}
