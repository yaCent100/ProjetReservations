package be.iccbxl.pid.reservationsSpringBoot.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.service.EmailService;
import be.iccbxl.pid.reservationsSpringBoot.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", User.createInstance());
		return "register/registration";
	}

	@PostMapping("/register")
	public String processRegistrationForm(@RequestParam String password, @ModelAttribute @Valid User user, BindingResult result, Model model) {
		if (password == null || password.isEmpty()) {
			return "register/registration";
		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register/registration";
		}

		if (userService.existsByEmail(user.getEmail())) {
			model.addAttribute("errorEmail", "Cet email est déjà utilisé. Veuillez en choisir un autre.");
			return "register/registration";
		}

		try {
			// Ajoutez l'utilisateur
			userService.addUser(user);
			model.addAttribute("registrationSuccess", true);


			String confirmationSubject = "Confirmation d'inscription";

			String sb = "Bienvenue " + user.getLogin() + "\n" +
					"Merci de vous être inscrit sur ShopUpNow.";

// 			Envoyez l'e-mail de confirmation

			emailService.sendConfirmationEmail(user.getEmail(), confirmationSubject, sb);


			return "redirect:/login?success=true";

		} catch (DataIntegrityViolationException ex) {
			model.addAttribute("errorLogin", "Une erreur s'est produite lors de l'inscription. Veuillez réessayer.");
			return "register";
		}
	}
}
