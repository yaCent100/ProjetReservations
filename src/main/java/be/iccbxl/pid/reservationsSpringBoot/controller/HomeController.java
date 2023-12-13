package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

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


	    return "home/home";
	}
	
	@PostMapping("/home")
	public String home() {
		return "redirect:/home";
	}
	
	

}
