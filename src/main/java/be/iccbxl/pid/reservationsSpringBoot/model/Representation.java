package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.slugify.Slugify;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name = "representations")
public class Representation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="show_id", nullable=false)
    private Show show;

    /**
     * Date de création de la représentation
     */
    private LocalDateTime when;

    /**
     * Lieu de prestation de la représentation
     */
    @ManyToOne
    @JoinColumn(name="location_id", nullable=true)
    private Location location;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "reservations",
            joinColumns = @JoinColumn(name = "representation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    
    @OneToMany(mappedBy = "representation")
    private List<Reservation> reservations = new ArrayList<>();


    public Representation addUser(User user) {
        if(!this.users.contains(user)) {
            this.users.add(user);
            user.addRepresentation(this);
        }

        return this;
    }

    public Representation removeUser(User user) {
        if(this.users.contains(user)) {
            this.users.remove(user);
            user.getRepresentations().remove(this);
        }

        return this;
    }

    



}
