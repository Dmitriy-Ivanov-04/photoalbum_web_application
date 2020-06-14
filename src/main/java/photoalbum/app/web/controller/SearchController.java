package photoalbum.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import photoalbum.app.domain.profile.ProfileService;


@Controller
public class SearchController {

    @GetMapping("/search")
    public String search(Model model) {

        return "resultSearch";
    }

}
