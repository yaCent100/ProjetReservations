package be.iccbxl.pid.reservationsSpringBoot.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

   /*@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();*/

   @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
    

    public List<User> getUsers() {
        return users;
    }

    public Role addUser(User user) {
        if(!this.users.contains(user)) {
            this.users.add(user);
            user.getRoles().add(this);
        }

        return this;
    }

    public Role removeUser(User user) {
        if(this.users.contains(user)) {
            this.users.remove(user);
            user.getRoles().remove(this);
        }

        return this;
    }
    
    
    @Override
    public String toString() {
        return " "+this.role+" ";
    }
    
    public static Role createInstance() {
    	return new Role();
    }


}
