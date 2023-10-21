package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	ShowService showService;
	
	@Autowired
	UserService userService;
	
	
	
	@GetMapping("/admin")
	public String index() {
		
		return "admin/main";
	}
	
	@GetMapping("/admin/shows")
	public String shows(Model model) {
		
		List<Show> shows = showService.getAll();

	    model.addAttribute("shows", shows);
	    model.addAttribute("title", "Liste des spectacles");

	    return "admin/shows";
	}
	
	@GetMapping("/admin/users")
	public String users(Model model) {
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("users", users);
		model.addAttribute("title", "Utilisateurs");
		
		return "admin/user";
	}
	
	
	

}
