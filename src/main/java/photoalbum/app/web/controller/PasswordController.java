package photoalbum.app.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.web.form.CodeForm;
import photoalbum.app.web.form.EmailForm;
import photoalbum.app.web.form.validator.EmailFormValidator;

@Controller
public class PasswordController {
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	private EmailFormValidator emailFormValidator;
	
	@InitBinder("emailForm")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(emailFormValidator);
	}
	
	@GetMapping("/recovery")
	public String recovery(Model model, EmailForm emailForm) {
			
		model.addAttribute("emailForm", emailForm);
		
		return "passwordRecovery";
	}
	
	@PostMapping("/recovery")
	public String recoveryPost(Model model, @Valid @ModelAttribute("emailForm") EmailForm emailForm, 
			BindingResult binding) {
		
		if (binding.hasErrors()) {
			model.addAttribute("emailForm", emailForm);
			return "passwordRecovery";
		}
		
		profileService.passwordRecovery(emailForm);
		
		return "redirect:/change_password";
	}	
	
	@GetMapping("/change_password")
	public String changePassword(Model model, CodeForm codeForm) {
		
		model.addAttribute("codeForm", codeForm);
		
		return "changePass";
	}	
	
	@PostMapping("/change_password")
	public String changePasswordPost(Model model, @ModelAttribute("codeForm") CodeForm codeForm,
			BindingResult binding) {
		
		if (binding.hasErrors()) {
			model.addAttribute("codeForm", codeForm);
			return "changePass";
		}
		
		profileService.saveCodeForCodeForm(codeForm);
		
		return "redirect:/login";
	}

}
