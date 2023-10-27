package be.iccbxl.pid.reservationsSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	UserService userService;
	
	
	
	@GetMapping("/register")
	public String show(Model model) {
		
		User user = User.createInstance();
		
		model.addAttribute("user", user);
		
		return "register/registration";
	}
	
	 @PostMapping("/register")
	    public String create(@ModelAttribute("user") User user) {
		 
	        userService.addUser(user);
	        
	        return "redirect:/login";
	    }
	 
	 

}
