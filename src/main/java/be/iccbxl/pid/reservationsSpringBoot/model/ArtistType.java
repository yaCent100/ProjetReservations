package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name = "artist_type")
public class ArtistType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="artist_id", nullable=false)
    private Artist artist;

    @ManyToOne
    @JoinColumn(name="type_id", nullable=false)
    private Type type;

    @ManyToMany(targetEntity=Show.class)
    @JoinTable(
            name = "artist_type_show",
            joinColumns = @JoinColumn(name = "artist_type_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id"))
    private List<Show> shows = new ArrayList<>();


    public ArtistType addShow(Show show) {
        if(!this.shows.contains(show)) {
            this.shows.add(show);
            show.addArtistType(this);
        }

        return this;
    }

    public ArtistType removeShow(Show show) {
        if(this.shows.contains(show)) {
            this.shows.remove(show);
            show.getArtistTypes().remove(this);
        }

        return this;
    }

}
