package be.iccbxl.pid.reservationsSpringBoot.repository;

import be.iccbxl.pid.reservationsSpringBoot.model.Location;
import be.iccbxl.pid.reservationsSpringBoot.model.Representation;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RepresentationRepository extends CrudRepository<Representation, Long> {

    List<Representation> findByShow(Show show);
    List<Representation> findByLocation(Location location);
    //List<Representation> findByWhen(LocalDateTime when);
	Representation findByWhen(LocalDateTime when);


}
