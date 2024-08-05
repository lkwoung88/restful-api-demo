package hello.rest.domain;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/article/createForm")
    public String createForm() {
        return "article/createForm";
    }

    @GetMapping(value = "/article/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "article/details";
    }
}
