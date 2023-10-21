package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name="localities")
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The postalCode must not be empty.")
    @Size(min=2, max=6, message = "The postalCode must be between 2 and 6 characters long.")
    private String postalCode;

    @NotEmpty(message = "The locality must not be empty.")
    @Size(min=2, max=60, message = "The locality must be between 2 and 60 characters long.")
    private String locality;

    @OneToMany( targetEntity=Location.class, mappedBy="locality" )
    private List<Location> locations = new ArrayList<>();


    public Locality addLocation(Location location) {
        if(!this.locations.contains(location)) {
            this.locations.add(location);
            location.setLocality(this);
        }
        return this;
    }
    public Locality removeLocation(Location location) {
        if(this.locations.contains(location)) {
            this.locations.remove(location);
            if(location.getLocality().equals(this)) {
                location.setLocality(null);
            }
        }
        return this;
    }

}
