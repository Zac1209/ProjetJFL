package cgg.informatique.jfl.webSocket.entite;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ROLES")
public class Role {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String role;

    @OneToMany(  mappedBy = "role" )
    private List<Compte> comptes  = new ArrayList<>();

    public Role(String id, String role, List<Compte> comptes) {
        this.id = id;
        this.role = role;
        this.comptes = comptes;
    }

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}
