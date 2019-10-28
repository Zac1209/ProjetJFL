package cgg.informatique.jfl.webSocket.entite;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name="COMPTES")
public class Compte implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String password;
    private String role;
    private String ceinture;
    private String avatar;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.role));
    }
    @Override
    public String getPassword() {
        return this.password ;
    }
    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Compte(String username, String role, String ceinture, String avatar) {
        this.username = username;
        this.role = role;
        this.ceinture = ceinture;
        this.avatar = avatar;
    }

    public Compte() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCeinture() {
        return ceinture;
    }

    public void setCeinture(String ceinture) {
        this.ceinture = ceinture;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
