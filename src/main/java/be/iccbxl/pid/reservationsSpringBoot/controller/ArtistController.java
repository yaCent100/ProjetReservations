package be.iccbxl.pid.reservationsSpringBoot.controller;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArtistController {

	@Autowired
	ArtistService artistService;

	@GetMapping("/artists")
	public String index(Model model) {

		List<Artist> artists = artistService.getAllArtists();

		model.addAttribute("artists", artists);
		model.addAttribute("title", "Liste des artistes");

		return "artist/index";
	}

	@GetMapping("/artists/{id}")
	public String show(Model model, @PathVariable("id") String id) {

		Artist artist = artistService.getArtist(id);

		model.addAttribute("artist", artist);
		model.addAttribute("title", "Fiche d'un artiste");

		return "artist/show";
	}

	@GetMapping("/artists/create")
	public String create(Model model) {
		Artist artist = Artist.createInstance();

		model.addAttribute("artist", artist);

		return "artist/create";
	}
	
	

	@PostMapping("/artists/create")
	public String store(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "artist/create";
		}

		artistService.addArtist(artist);

		return "redirect:/artists/" + artist.getId();
	}

	@GetMapping("/artists/{id}/edit")
	public String edit(Model model, @PathVariable("id") String id, HttpServletRequest request) {
		Artist artist = artistService.getArtist(id);

		model.addAttribute("artist", artist);

		// Générer le lien retour pour l'annulation
		String referrer = request.getHeader("Referer");

		if (referrer != null && !referrer.equals("")) {
			model.addAttribute("back", referrer);
		} else {
			model.addAttribute("back", "/artists/" + artist.getId());
		}

		return "artist/edit";
	}

	@PutMapping("/artists/{id}/edit")
	public String update(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult,
			@PathVariable("id") String id, Model model) {

		if (bindingResult.hasErrors()) {
			return "artist/edit";
		}

		Artist existing = artistService.getArtist(id);

		if (existing == null) {
			return "artist/index";
		}

		Long indice = (long) Integer.parseInt(id);

		artist.setId(indice);
		artistService.updateArtist(String.valueOf(artist.getId()), artist);

		model.addAttribute("artist", artist);

		return "redirect:/artists/" + artist.getId();
	}

	@DeleteMapping("/artists/{id}")
	public String delete(@PathVariable("id") String id, Model model) {
		Artist existing = artistService.getArtist(id);

		if (existing != null) {
			Long indice = (long) Integer.parseInt(id);

			artistService.deleteArtist(String.valueOf(indice));
		}

		return "redirect:/artists";
	}

}
