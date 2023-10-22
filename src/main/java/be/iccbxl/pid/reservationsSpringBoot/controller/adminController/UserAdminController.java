package be.iccbxl.pid.reservationsSpringBoot.controller.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;
import jakarta.validation.Valid;

@Controller
public class UserAdminController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/admin/users")
	public String users(Model model) {
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("users", users);
		model.addAttribute("title", "Utilisateurs");
		
		return "admin/user/users";
	}
	
	@GetMapping("/admin/add-user")
	public String create(Model model) {
		
		User user = User.createInstance();
		model.addAttribute("user", user);
		
		return "admin/user/add-user";
	}
	
	
	@PostMapping("/admin/add-user")
	public String Store(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "admin/user/add-user";
	    }

	    userService.addUser(user);

	    return "redirect:/admin/users/" + user.getId();
	}
	
	
	

}
