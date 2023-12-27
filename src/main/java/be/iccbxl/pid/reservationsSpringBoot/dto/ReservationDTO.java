package be.iccbxl.pid.reservationsSpringBoot.dto;

import be.iccbxl.pid.reservationsSpringBoot.model.Representation;
import be.iccbxl.pid.reservationsSpringBoot.model.User;
import lombok.Data;

@Data
public class ReservationDTO {

	    private int nbPlaces;
	    private Representation representation;
	    private User user;

	
}
