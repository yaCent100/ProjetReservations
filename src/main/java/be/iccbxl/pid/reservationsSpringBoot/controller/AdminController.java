package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;

@Controller
public class AdminController {
	
	@Autowired
	ShowService showService;
	
	
	
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
	
	
	

}
