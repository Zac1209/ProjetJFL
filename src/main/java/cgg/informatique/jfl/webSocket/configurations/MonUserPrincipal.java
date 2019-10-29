package cgg.informatique.jfl.webSocket.configurations;

import cgg.informatique.jfl.webSocket.entite.Compte;
import cgg.informatique.jfl.webSocket.entite.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MonUserPrincipal implements UserDetails {
    private Compte compte;
    public MonUserPrincipal(Compte compte) {
        if (compte != null)
            this.compte = compte;
        else
            this.compte = new Compte();
    }
    public Role getRole() { return compte.getRole();}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return compte.getAuthorities();
    }
    @Override
    public String getPassword() { return compte.getPassword();}
    @Override
    public String getUsername() { return compte.getUsername();}
    @Override
    public boolean isAccountNonExpired() { return compte.isAccountNonExpired();}
    @Override
    public boolean isAccountNonLocked() { return compte.isAccountNonLocked();}
    @Override
    public boolean isCredentialsNonExpired() { return
            compte.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() { return compte.isEnabled(); }
    @Override
    public String toString() {
        return "Compte{" +
                "username='" + compte.getUsername() + '\'' +
                ", password='" + compte.getPassword() + '\'' +
                ", role='" + compte.getRole() + '\'' +
                '}';
    }
}
