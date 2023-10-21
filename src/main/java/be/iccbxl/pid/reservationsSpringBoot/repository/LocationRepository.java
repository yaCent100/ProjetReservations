package be.iccbxl.pid.reservationsSpringBoot.repository;

import be.iccbxl.pid.reservationsSpringBoot.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findByDesignation(String designation); 
    Optional<Location> findById(Long id);
}
