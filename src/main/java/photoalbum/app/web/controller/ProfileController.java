package photoalbum.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import photoalbum.app.data.PhotoStorage;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.data.RelationshipsStorage;
import photoalbum.app.domain.model.Status;
import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.domain.relationships.RelationshipsService;
import photoalbum.app.spring.ProfileDetailsImpl;

@Controller
public class ProfileController {

	@Autowired
	ProfileService profileService;

	@Autowired
	ProfileStorage profileStorage;
	
	@Autowired
	PhotoStorage photoStorage;
	
	@Autowired
	RelationshipsStorage relationshipsStorage;
	
	@Autowired
	RelationshipsService relationshipsServise;

	
	@GetMapping("/user/{nickname}")
	public String profile(Model model, @PathVariable String nickname) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(nickname);
		model.addAttribute("nickname", profileDetails.getNickname());
		model.addAttribute("nicknameView", nickname);
		model.addAttribute("publications", photoStorage.countPublicationsByUser(profileId));
		model.addAttribute("friends", relationshipsStorage.findFriends(profileId).size());
		model.addAttribute("followers", relationshipsStorage.findFollowers(profileId).size());
		model.addAttribute("subscribes", relationshipsStorage.findSubscriptions(profileId).size());
		model.addAttribute("profileId", profileId);
		model.addAttribute("deleteFriend", "Delete friend");
	
		return "profile/profile";
	}
	
}
