package photoalbum.app.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import photoalbum.app.data.ProfileStorage;
import photoalbum.app.data.profile.ProfileStorageDAO;
import photoalbum.app.domain.mail.MailSender;
import photoalbum.app.domain.model.Profile;
import photoalbum.app.domain.profile.ProfileService;
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

	/*@Autowired
	Profile existingUser;*/

	@Autowired
	private MailSender mailSender;

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

		return "redirect:/";
	}
/*
	// Display the form
	@RequestMapping(value="/forgot-password", method=RequestMethod.GET)
	public ModelAndView displayResetPassword(ModelAndView modelAndView, Profile user) {
		modelAndView.addObject("profile", user);
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
	}

	// Receive the address and send an email
	@RequestMapping(value="/forgot-password", method=RequestMethod.POST)
	public ModelAndView forgotUserPassword(ModelAndView modelAndView, String email) {
		existingUser = profileService.getProfileByEmail(email);
		if (existingUser != null) {
			String messageText = "To complete the password reset process, please click here: "
					+ "http://localhost:8080/confirm-reset?token=" + existingUser.getToken();
			// Send the email
			mailSender.send(existingUser.getEmail(), "Recovery Password", messageText);

			modelAndView.addObject("message", "Request to reset password received. Check your inbox for the reset link.");
			modelAndView.setViewName("successForgotPassword");

		} else {
			modelAndView.addObject("message", "This email address does not exist!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	// Endpoint to update a user's password
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView resetUserPassword(ModelAndView modelAndView, String email) {
		existingUser = profileService.getProfileByEmail(email);
		if (existingUser.getEmail() != null) {
			// Use email to find user
			existingUser.setPassword("NewPassword");
			profileStorage.save(existingUser);
			modelAndView.addObject("message", "Password successfully reset. You can now log in with the new credentials.");
			modelAndView.setViewName("successResetPassword");
		} else {
			modelAndView.addObject("message","The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
	*/
}
