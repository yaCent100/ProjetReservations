package be.iccbxl.pid.reservationsSpringBoot.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import be.iccbxl.pid.reservationsSpringBoot.model.Representation;
import be.iccbxl.pid.reservationsSpringBoot.model.Reservation;
import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.security.CustomUserDetails;
import be.iccbxl.pid.reservationsSpringBoot.service.ReservationService;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;


@Controller
public class ProfilController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReservationService reservationService;


	@GetMapping("/account")
	public String showProfilePage(Model model) {
	    CustomUserDetails userDetails = getCurrentUserDetails();
	    if (userDetails != null) {
	        model.addAttribute("user", userDetails);
	    }
	    return "user/profil";
	}

	@GetMapping("/reservations")
	public String showReservation(Model model) {
	    CustomUserDetails userDetails = getCurrentUserDetails();
	   
	    if (userDetails != null) {
	    	  User currentUser = userService.findByLogin(userDetails.getUsername()); // Remplacez cela par votre façon de récupérer l'utilisateur depuis votre repository
	          
	    	  if (currentUser != null) {
	              List<Reservation> userReservations = reservationService.getReservationsByUser(currentUser); // Obtenez les réservations liées à l'utilisateur
	              model.addAttribute("userReservations", userReservations);
	          }
	    	
	    }
	    return "user/reservation";
	}
	
	
	private CustomUserDetails getCurrentUserDetails() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
	        return (CustomUserDetails) authentication.getPrincipal();
	    }
	    return null;
	}
	






}
	