package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name="types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The type must not be empty.")
    @Size(min=2, max=60, message = "The type must be between 2 and 60 characters long.")
    private String type;

    @ManyToMany
    @JoinTable(
            name = "artist_type",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> artists = new ArrayList<>();


    public Type addArtist(Artist artist) {
        if(!this.artists.contains(artist)) {
            this.artists.add(artist);
            artist.addType(this);
        }

        return this;
    }

    public Type removeType(Artist artist) {
        if(this.artists.contains(artist)) {
            this.artists.remove(artist);
            artist.getTypes().remove(this);
        }

        return this;
    }

}
