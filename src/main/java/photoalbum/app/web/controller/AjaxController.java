package photoalbum.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import photoalbum.app.data.ProfileStorage;
import photoalbum.app.data.RelationshipsStorage;
import photoalbum.app.domain.dto.ProfileJsonDTO;
import photoalbum.app.domain.model.Relationships;
import photoalbum.app.domain.model.Status;
import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.spring.ProfileDetailsImpl;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
	@Autowired
	RelationshipsStorage relationshipsStorage;
	
	@Autowired
	ProfileStorage profileStorage;
	
	@Autowired
	ProfileService profileService;
	
	@RequestMapping(value = "/add-friend")
	public void addFriend(@RequestParam("n") String nickname) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Long profileId = profileStorage.getIdByNickname(nickname);
		Long loginProfileId = profileStorage.getIdByNickname(profileDetails.getNickname());
		if(loginProfileId != profileId) {
			Relationships relationship;
			try {
				relationship = relationshipsStorage.findRelationshipsByUsers(loginProfileId, profileId);
				Status status = relationship.getStatus();
				if(status == Status.SUBSCRIBER)
					relationshipsStorage.unsubscribe(relationship.getId());
				if (status == Status.FRIEND)
					relationshipsStorage.deleteFriend(profileId, loginProfileId, relationship.getId());
			} catch(EmptyResultDataAccessException e) {
				try {
					relationship = relationshipsStorage.findRelationshipsByUsers(profileId, loginProfileId);
					Status status = relationship.getStatus();
					if(status == Status.SUBSCRIBER)
						relationshipsStorage.acceptInvite(relationship.getId());
					if (status == Status.FRIEND)
						relationshipsStorage.deleteFriend(profileId, loginProfileId, relationship.getId());
				} catch(EmptyResultDataAccessException e1) {
					relationshipsStorage.sendInvite(loginProfileId, profileId);
				}
			}
		}
	}
	
	@RequestMapping(value = "/my-profile") //, produces = MediaType.APPLICATION_JSON_VALUE
	public boolean showAddFriendButton(@RequestParam("n") String nick) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(profileDetails.getNickname().equals(nick))	
			return true;
		else
			return false;
	}
	
	@RequestMapping(value = "/friend-list/{divId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProfileJsonDTO> friendList(@RequestParam("n") String nick, @PathVariable String divId) {
		return profileService.usersByUserAsJson(profileStorage.getIdByNickname(HtmlUtils.htmlEscape(nick)), HtmlUtils.htmlEscape(divId));
	}
}
