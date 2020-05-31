package photoalbum.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import photoalbum.app.data.ProfileStorage;
import photoalbum.app.domain.model.Profile;


@Controller
public class UserController {
	
	@Autowired
	ProfileStorage profileStorage;
	
	@GetMapping("/")
	public String profile(@RequestParam(name="nickname", required=false, defaultValue="Nickname") String nickname, Model model) {
		Profile profile = new Profile();
		profile = profileStorage.getProfileByNickname(nickname);
		model.addAttribute("nickname", profile.getNickname());
		return "profile/profile";
	}

}
