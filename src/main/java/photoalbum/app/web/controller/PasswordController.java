package photoalbum.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.web.form.CodeForm;
import photoalbum.app.web.form.EmailForm;

@Controller
public class PasswordController {
	
	@Autowired
	ProfileService profileService;
	
	@GetMapping("/recovery")
	public String recovery(Model model, EmailForm emailForm) {
			
		model.addAttribute("emailForm", emailForm);
		
		return "passwordRecovery";
	}
	
	@PostMapping("/recovery")
	public String recoveryPost(Model model, @ModelAttribute("emailForm") EmailForm emailForm) {
		
		profileService.passwordRecovery(emailForm);
		
		return "redirect:/change_password";
	}	
	
	@GetMapping("/change_password")
	public String changePassword(Model model, CodeForm codeForm) {
		
		model.addAttribute("codeForm", codeForm);
		
		return "changePass";
	}	
	
	@PostMapping("/change_password")
	public String changePasswordPost(Model model, @ModelAttribute("codeForm") CodeForm codeForm) {
		
		profileService.saveCodeForCodeForm(codeForm);
		
		return "redirect:/login";
	}

}
