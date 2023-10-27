package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;

@Controller
public class HomeController {
	
	@Autowired
	ShowService showService;
	
	
	@GetMapping("/home")
	public String home(Model model) {
		
		  List<Show> shows = showService.getAll();

	        model.addAttribute("shows", shows);
	        
		return "layouts/main";
	}

	

}
