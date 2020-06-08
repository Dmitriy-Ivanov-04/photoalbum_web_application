package photoalbum.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import photoalbum.app.domain.profile.ProfileService;

@Controller
public class ConfirmMailController {
	
	@Autowired
	ProfileService profileService;

	@GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
		boolean isActivated = profileService.activateProfile(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
            return "confirm";
        } else {
            model.addAttribute("message", "Activation code is not found!");
            return "profile/registration";
        }

    }

	@GetMapping("/confirm")
	public String confirm(){
		return "confirm";
	}
	
}
