package photoalbum.app.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import photoalbum.app.data.PhotoStorage;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.data.RelationshipsStorage;
import photoalbum.app.domain.mail.MailClient;
import photoalbum.app.domain.model.Profile;
import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.spring.ProfileDetailsImpl;
import photoalbum.app.web.form.ProfileRegistrationForm;
import photoalbum.app.web.form.validator.ProfileRegistrationFormValidator;


@Controller
public class ProfileController {

	@Autowired
	ProfileService profileService;

	@Autowired
	private ProfileRegistrationFormValidator profileValidator;

	@Autowired
	ProfileStorage profileStorage;
	
	@Autowired
	PhotoStorage photoStorage;
	
	@Autowired
	RelationshipsStorage relationshipsStorage;
	
	@Autowired
	MailClient mailClient;
	
	Profile profile;

	@InitBinder("profileForm")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(profileValidator);
	}
	
	@GetMapping("/profile/registration")
	public String registration(Model model, ProfileRegistrationForm userForm) {

		model.addAttribute("profileForm", userForm);

		return "profile/registration";
	}

	@PostMapping("/profile/registration")
	public String registrationPost(Model model, @Valid @ModelAttribute("profileForm") ProfileRegistrationForm profileForm,
			BindingResult binding) {

		if (binding.hasErrors()) {
			model.addAttribute("profileForm", profileForm);
			return "profile/registration";
		}

		profileService.createUserFromRegistrationForm(profileForm);
		mailClient.sendMail("rfln.support@gmail.com", profileForm.getEmail(), "Registration", "Hello! Registration completed!");


		return "redirect:/";
	}
	
	@GetMapping("/user/{nickname}")
	public String profile(Model model, @PathVariable String nickname) {
		ProfileDetailsImpl profileDetails = (ProfileDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long profileId = profileStorage.getIdByNickname(nickname);
		model.addAttribute("nickname", nickname);
		model.addAttribute("publications", photoStorage.countPublicationsByUser(profileId));
		model.addAttribute("subscribes", relationshipsStorage.findSubscriptions(profileId).size());
		//model.addAttribute("friends", relationshipsStorage.findFriends(profileId).size()); //переименовать people в target в sql
		model.addAttribute("followers", relationshipsStorage.findSubscriptions(profileId).size());
		if(nickname.equals(profileDetails.getNickname())) {
			return "profile/profile";
		} else {
			return "profile/profile1";
		}
	}
	
	@GetMapping("/recovery")
	public String recovery() {
		return "/passwordRecovery";
	}

	@GetMapping("/change_password")
	public String changePassword(){
		return "/changePass";
	}
}


