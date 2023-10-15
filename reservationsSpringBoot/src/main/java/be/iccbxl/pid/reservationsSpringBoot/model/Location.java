package be.iccbxl.pid.reservationsSpringBoot.model;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String slug;
    private String designation;
    private String address;

    @ManyToOne
    @JoinColumn(name="locality_id", nullable=false)
    private Locality locality;
    private String website;
    private String phone;

    public Location(String slug, String designation, String address, Locality locality, String website, String
            phone) {
        Slugify slg = new Slugify();
        this.slug = slg.slugify(designation);
        this.designation = designation;
        this.address = address;
        this.locality = locality;
        this.website = website;
        this.phone = phone;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
        Slugify slg = new Slugify();
        this.setSlug(slg.slugify(designation));
    }

    public void setLocality(Locality locality) {
        this.locality.removeLocation(this);     //déménager de l’ancienne localité
        this.locality = locality;
        this.locality.addLocation(this);        //emménager dans la nouvelle localité
    }


}
