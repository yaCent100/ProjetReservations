package be.iccbxl.pid.reservationsSpringBoot.controller;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

        return "redirect:/artists/"+artist.getId();
    }



}
