package be.iccbxl.pid.reservationsSpringBoot.controller;

import be.iccbxl.pid.reservationsSpringBoot.model.Artist;
import be.iccbxl.pid.reservationsSpringBoot.model.ArtistType;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ShowController {

    @Autowired
    ShowService showService;

    @GetMapping("/shows")
    public String index(Model model) {
        List<Show> shows = showService.getAll();

        model.addAttribute("shows", shows);
        model.addAttribute("title", "Liste des spectacles");

        return "show/index";
    }

    @GetMapping("/shows/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Show show = showService.get(id);

        //Récupérer les artistes du spectacle et les grouper par type
        Map<String, ArrayList<Artist>> collaborateurs = new TreeMap<>();

        for(ArtistType at : show.getArtistTypes()) {
            String type = at.getType().getType();

            if(collaborateurs.get(type) == null) {
                collaborateurs.put(type, new ArrayList<>());
            }

            collaborateurs.get(type).add(at.getArtist());
        }

        model.addAttribute("collaborateurs", collaborateurs);
        model.addAttribute("show", show);
        model.addAttribute("title", "Fiche d'un spectacle");

        return "show/show";
    }



}
