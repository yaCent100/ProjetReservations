package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.reservationsSpringBoot.model.Role;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.RoleService;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	//--------------DASHBOARD-------------//
	
	@GetMapping
	public String dashboard(Model model) {
		List<User> users = userService.getAllUsers();
		int totalUsers = users.size();
		
		List<Show> shows = showService.getAll();
		int totalShows = shows.size();
		
		 model.addAttribute("totalUsers", totalUsers);
		 model.addAttribute("totalShows", totalShows);
		
		 
		return "admin/main";
	}
	

	//--------------- USER ----------------//
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/users")
	public String users(Model model) {
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("users", users);
		model.addAttribute("title", "Utilisateurs");
		
		return "admin/user/users";
	}
	
	@GetMapping("/add-user")
	public String createUser(Model model) {
		
		User user = User.createInstance();
		List<Role> roles = roleService.getAll();
		
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		
		return "admin/user/add-user";
	}
	
	
	@PostMapping("/add-user")
	public String store(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, @RequestParam("roles") List<String> roleIds) {
	    if (bindingResult.hasErrors()) {
	        return "admin/user/add-user";
	    }
	  
	    for (String roleId : roleIds) {
	        Role role = roleService.get(roleId);
	        user.getRoles().add(role); // Ajoute les rôles existants à l'utilisateur
	    }

	    userService.addUser(user); // Ajoute l'utilisateur avec les rôles

	    return "redirect:/admin/users";
	}
	
	
	//------------- SHOW -------------//
	
	@Autowired
	ShowService showService;
	
	@GetMapping("/shows")
	public String shows(Model model) {
		 List<Show> shows = showService.getAll();

	     model.addAttribute("shows", shows);
	        
	    return "admin/show/shows";
	}
	
	@GetMapping("/add-show")
	public String createShow(Model model) {
		
		Show show = Show.createInstance();
		model.addAttribute("show", show);
		
		return "admin/show/add-show";
	}
	

	@PostMapping("/add-show")
	public String addShow(@Valid @ModelAttribute("show") Show show, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "/add-show";
	    }

	    showService.add(show);

	    return "redirect:/admin/shows";
	}
	
	
	@GetMapping("/{id}/edit")
	public String editShow(Model model, @PathVariable("id") String id, HttpServletRequest request) {
		Show show = showService.get(id);

		model.addAttribute("show", show);

		// Générer le lien retour pour l'annulation
		String referrer = request.getHeader("Referer");

		if (referrer != null && !referrer.equals("")) {
			model.addAttribute("back", referrer);
		} else {
			model.addAttribute("back", "/artists/" + show.getId());
		}

		return "admin/show/edit";
	}
	
	@PutMapping("/{id}/edit")
	public String updateShow(@Valid @ModelAttribute("show") Show show, BindingResult bindingResult,
			@PathVariable("id") String id, Model model) {

		if (bindingResult.hasErrors()) {
			return "/admin/edit";
		}

		Show existing = showService.get(id);

		if (existing == null) {
			return "/admin/show/shows";
		}

		Long indice = (long) Integer.parseInt(id);

		show.setId(indice);
		showService.update(String.valueOf(show.getId()), show);

		model.addAttribute("show", show);

		return "redirect:/admin/shows";
	}
	
	@DeleteMapping("/shows/{id}")
	public String deleteShow(@PathVariable("id") String id, Model model) {
		Show existing = showService.get(id);

		if (existing != null) {
			Long indice = (long) Integer.parseInt(id);

			showService.delete(String.valueOf(indice));
		}

		return "redirect:/admin/shows";
	}
	
	
}
