package photoalbum.app.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import photoalbum.app.domain.model.Profile;
import photoalbum.app.domain.photo.PhotoServiceDomain;
import photoalbum.app.domain.profile.ProfileService;
import photoalbum.app.spring.ProfileDetailsImpl;
import photoalbum.app.web.form.UploadForm;

@Controller
public class BackgroundController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private PhotoServiceDomain photoService;
	
	@GetMapping("/photo/background-upload")
    public ModelAndView avatarUploads(ModelAndView modelAndView, @RequestParam("profileId") Optional<Long> profileId, Model model, UploadForm uploadForm) {
    	if (profileId.isEmpty()) {
    		modelAndView.setViewName("redirect:/");
    	} else {
    		modelAndView.addObject("profileId", profileId.get());
            ProfileDetailsImpl profileDetails = (ProfileDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("nickname", profileDetails.getNickname());
    		modelAndView.setViewName("/photo/background-upload");
    	}
        return modelAndView;
    }

    @PostMapping("/photo/background-upload")
    public ModelAndView avatarUploadProcessings(@RequestParam("files") MultipartFile[] files, @RequestParam("profileId") Optional<Long> profileId,
                                               ModelAndView modelAndView, Model model) {
	    modelAndView.setViewName("redirect:/");

        if (profileId.isPresent()) {
        	Profile profile = profileService.findById(profileId.get());

        	if (profile != null) {
                for (MultipartFile multipartFile : files) {
                	if (!photoService.saveAvatarAndBackground(multipartFile, profileId.get())) {
                		modelAndView.setViewName("redirect:/photo/upload-error");
                		break;
                	}
                }
        	}
        }

        return modelAndView;
    }       
    
    @GetMapping(value="/background/{photoId}", produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource photos(ModelAndView modelAndView, @PathVariable Long photoId) {
        return photoService.getImage(photoId);
    }

}
