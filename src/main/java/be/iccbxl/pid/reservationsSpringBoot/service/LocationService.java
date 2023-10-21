package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Location;
import be.iccbxl.pid.reservationsSpringBoot.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAll() {
        List<Location> locations = new ArrayList<>();
        locationRepository.findAll().forEach(locations::add); return locations;
    }
    public Location get(String id) {
        Long indice = (long) Integer.parseInt(id);
        Optional<Location> location = locationRepository.findById(indice);
        return location.orElse(null);
    }
    public void add(Location location) {
        locationRepository.save(location);
    }
    public void update(String id, Location location) {
        locationRepository.save(location);
    }
    public void delete(String id) {
        Long indice = (long) Integer.parseInt(id);
        locationRepository.deleteById(indice);
    }
}
