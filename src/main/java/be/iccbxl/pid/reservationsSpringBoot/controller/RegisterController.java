package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.reservationsSpringBoot.model.Role;
import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.RoleService;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@GetMapping("/register")
	public String create(Model model) {
		User user = User.createInstance();
		model.addAttribute("user", user);
		return "register/registration";
	}

	@PostMapping("/register")
	public String processRegistrationForm(@RequestParam String login, @RequestParam String password,
			@RequestParam String email, @RequestParam String roleName) {
		 User newUser = User.createInstance(); // Créer une nouvelle instance d'utilisateur

		    // Configurer les champs de l'utilisateur
		    newUser.setLogin(login);
		    newUser.setPassword(password);
		    newUser.setEmail(email);
		    
		 // Définir le rôle par défaut comme "member" si aucun rôle n'est spécifié
		    if (roleName == null || roleName.isEmpty()) {
		        roleName = "member"; // Définir le rôle par défaut
		    }

		    Role role = roleService.findByRole(roleName); // Trouver le rôle par son nom
		    newUser.getRoles().add(role); // Ajouter le rôle à l'utilisateur

		    userService.addUser(newUser); // Ajouter l'utilisateur avec le rôle

		    return "redirect:/login"; // Rediriger vers la page de connexio
	}
}
