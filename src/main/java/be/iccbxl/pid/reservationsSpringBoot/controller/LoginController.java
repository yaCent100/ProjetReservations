package be.iccbxl.pid.reservationsSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLogin(Model model) {
    	
        model.addAttribute("user", User.createInstance()); 
        
        return "login/login"; 
    }

    @PostMapping("/processlogin")
    public String processLogin(@ModelAttribute("user") User user) {
        User existingUser = userService.finByLogin(user.getLogin());

        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return "redirect:/home";
        } else {
            return "redirect:/login?error";
        }
    }
}

