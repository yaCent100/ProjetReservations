package be.iccbxl.pid.reservationsSpringBoot.controller;


import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.security.CustomUserDetails;


@Controller
public class ProfilController {


	@GetMapping("/profil")
	public String showProfilePage(Model model, Principal principal) {
	    if (principal instanceof Authentication) {
	        Authentication authentication = (Authentication) principal;
	        if (authentication.getPrincipal() instanceof CustomUserDetails) {
	            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

	            model.addAttribute("user", userDetails);
	        }
	    }

	    return "profil/profil";
	}
	






}
	