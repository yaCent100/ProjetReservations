package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AdminController {
	
	@Autowired
	ShowService showService;
	

	
	
	
	@GetMapping("/admin")
	public String index() {
		
		return "admin/main";
	}
	

	



	
	
	

	
	@PostMapping("/admin/user/add-user")
	public String userStore(@Valid @ModelAttribute("user") Show show, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "users/add-user";
	    }

	    showService.add(show);

	    return "redirect:/admin/users/" + show.getId();
	}
	
	
	
	
	

}
