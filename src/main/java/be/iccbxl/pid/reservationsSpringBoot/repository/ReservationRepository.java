package be.iccbxl.pid.reservationsSpringBoot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.reservationsSpringBoot.model.Representation;
import be.iccbxl.pid.reservationsSpringBoot.model.Reservation;
import be.iccbxl.pid.reservationsSpringBoot.model.User;

public interface ReservationRepository extends CrudRepository<Reservation, Long>{
	
    Reservation findByRepresentation(Representation representation);

	List<Reservation> findReservationsByUser(User currentUser);



}
