package be.iccbxl.pid.reservationsSpringBoot.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.reservationsSpringBoot.model.Role;
import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	UserService userService;


	@GetMapping("/register")
	public String register(Model model) {
	    model.addAttribute("user", User.createInstance());

		return "register/registration";
	}

	@PostMapping("/register")
	public String processRegistrationForm(@RequestParam String password, @ModelAttribute User user, BindingResult result, Model model) {
	    if (password == null || password.isEmpty()) {
	        return "redirect:/register";
	    }
	   
	    if (result.hasErrors()) {
	        return "register"; // Retourne la vue register sans redirection
	    }

	    try {
	        userService.addUser(user);
	        return "redirect:/login";
	    } catch (DataIntegrityViolationException ex) {
	        model.addAttribute("errorLogin", "Ce login est déjà utilisé. Veuillez en choisir un autre.");
	        return "register"; // Retourne simplement la vue register sans redirection
	    }
	}


}
