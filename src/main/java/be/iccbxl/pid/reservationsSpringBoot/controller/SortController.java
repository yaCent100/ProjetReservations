package be.iccbxl.pid.reservationsSpringBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;

@RestController
public class SortController {

	@Autowired
    private ShowService showService;

    @GetMapping("/trier-par-titre")
    public ResponseEntity<List<Show>> sortByName() {
        List<Show> sortedShows = showService.getAllShowsSortedByTitle();
        return ResponseEntity.ok(sortedShows);
    }

   /* @GetMapping("/sort-by-price")
    public ResponseEntity<List<Show>> sortByPrice() {
        List<Show> sortedShows = showService.getAllShowsSortedByPrice();
        return ResponseEntity.ok(sortedShows);
    }*/

    // Autres méthodes de tri/filtrage
}
