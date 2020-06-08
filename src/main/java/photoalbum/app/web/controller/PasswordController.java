package photoalbum.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordController {
	
	@GetMapping("/recovery")
	public String recovery() {
		return "/passwordRecovery";
	}

	@GetMapping("/change_password")
	public String changePassword(){
		return "/changePass";
	}

}
