package be.iccbxl.pid.reservationsSpringBoot.repository;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.reservationsSpringBoot.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long>{
	

}
