package be.iccbxl.pid.reservationsSpringBoot.controller.adminController;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import be.iccbxl.pid.reservationsSpringBoot.controller.ShowController;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;
import jakarta.validation.Valid;

@RequestMapping("/admin")
@Controller
public class ShowAdminController extends ShowController {
	
	@Autowired
	ShowService showService;
	
	@Override
	@GetMapping("/shows")
	public String index(Model model) {
		super.index(model);

	    return "admin/show/shows";
	}
	
	@GetMapping("/add-show")
	public String create(Model model) {
		
		Show show = Show.createInstance();
		model.addAttribute("show", show);
		
		return "admin/show/add-show";
	}
	

	@PostMapping("/add-show")
	public String store(@Valid @ModelAttribute("show") Show show, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "show/add-show";
	    }

	    showService.add(show);

	    return "redirect:/admin/show/" + show.getId();
	}
	
	
	
	

}
