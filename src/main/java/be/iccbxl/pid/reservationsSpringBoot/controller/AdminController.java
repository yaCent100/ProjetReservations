package be.iccbxl.pid.reservationsSpringBoot.controller;

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
	
	@GetMapping()
	public String dashboard(Model model) {
		List<User> users = userService.getAllUsers();
		int totalUsers = users.size();
		
		List<Show> shows = showService.getAll();
		int totalShows = shows.size();
		
		 model.addAttribute("totalUsers", totalUsers);
		 model.addAttribute("totalShows", totalShows);
		
		 
		return "admin/dashboard/dashboard";
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
		model.addAttribute("title", "Users");
		
		return "admin/user/users";
	}
	
	@GetMapping("/add-user")
	public String createUser(Model model) {
		
		User user = User.createInstance();
		
		model.addAttribute("user", user);
		
		return "admin/user/add-user";
	}
	
	@PostMapping("/add-user")
	public String store(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(name = "roles", required = false) List<String> roleIds, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "admin/user/add-user";
	    }

	    // Utiliser la méthode addUser du service pour ajouter un utilisateur avec mot de passe crypté
	    userService.addUser(user);

	    // Ajouter les rôles à l'utilisateur nouvellement créé
	    if (roleIds != null) {
	        StringBuilder rolesStringBuilder = new StringBuilder();
	        for (String roleId : roleIds) {
	            Role role = roleService.findByRole(roleId);
	            if (role != null) {
	                rolesStringBuilder.append(role.getRole()).append(", ");
	            } else {
	                // Gérer le cas où le rôle n'est pas trouvé
	                System.out.println("Le rôle avec l'ID " + roleId + " n'a pas été trouvé.");
	            }
	        }

	        // Supprimer la virgule et l'espace final si des rôles ont été ajoutés
	        if (rolesStringBuilder.length() > 0) {
	            rolesStringBuilder.setLength(rolesStringBuilder.length() - 2);
	        }
	    }

	    return "redirect:/admin/users";
	}
	
	@GetMapping("users/{id}/edit")
	public String editUser(Model model, @PathVariable("id") long id, HttpServletRequest request) {
	    User user = userService.getUser(id);

	    model.addAttribute("user", user);

	    List<Role> roles = roleService.getAll(); 

	    model.addAttribute("roles", roles);

	    String referrer = request.getHeader("Referer");

	    if (referrer != null && !referrer.equals("")) {
	        model.addAttribute("back", referrer);
	    } else {
	        model.addAttribute("back", "/users");
	    }

	    return "admin/user/edit";
	}
	
	
	@PutMapping("/users/{id}/edit")
	public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
	        @PathVariable("id") long id, Model model) {

	    if (bindingResult.hasErrors()) {
	        return "/admin/user/edit";
	    }

	    User existing = userService.getUser(id);

	    if (existing == null) {
	        return "redirect:/admin/users";
	    }


	    user.setId(id);
	    userService.updateUser(String.valueOf(user.getId()), user);

	    model.addAttribute("user", user);

	    return "redirect:/admin/users";
	}
	
	/*@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable("id") String id, Model model) {
	    User existing = userService.getUser(id);

	    if (existing != null) {
	        for (Role role : existing.getRoles()) {
	            existing.removeRole(role);
	        }
	        userService.deleteUser(id);
	    }

	    return "redirect:/admin/users";
	}*/
	
	
	//------------- SHOW -------------//
	
	@Autowired
	ShowService showService;
	
	@GetMapping("/shows")
	public String shows(Model model) {
		 List<Show> shows = showService.getAll();

	     model.addAttribute("shows", shows);
	     model.addAttribute("title", "Shows");
	        
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
			model.addAttribute("back", "/shows");
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

			showService.delete(id);
		}

		return "redirect:/admin/shows";
	}
	
	
}
