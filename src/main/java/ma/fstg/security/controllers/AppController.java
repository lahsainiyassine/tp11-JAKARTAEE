package ma.fstg.security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/home"})
    public String home(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities());
        }
        return "home";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities());
        return "admin/dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities());
        return "user/dashboard";
    }
}
