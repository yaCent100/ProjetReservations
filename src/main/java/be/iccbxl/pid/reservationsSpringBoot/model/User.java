package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    @NotBlank(message = "Le login ne peut être vide")
    private String login;
    
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String langue;
    private LocalDateTime created_at;

  /* @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<>();*/
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Representation> representations = new ArrayList<>();

	
    public User(String login, String password, String firstname, String lastname, String email, String langue) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.langue = langue;
        this.created_at = LocalDateTime.now();
        this.roles = new ArrayList<>();
    }

    public User addRole(Role role) {
        if(!this.roles.contains(role)) {
            this.roles.add(role);
            role.getUsers().add(this);
        }

        return this;
    }

    public User removeRole(Role role) {
        if(this.roles.contains(role)) {
            this.roles.remove(role);
            role.getUsers().remove(this);
        }

        return this;
    }

    public User addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.addUser(this);
        }

        return this;
    }

    public User removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            representation.getUsers().remove(this);
        }

        return this;
    }

    public static User createInstance() {
    	return new User();
    }

}
