package cgg.informatique.jfl.webSocket.configurations;

import cgg.informatique.jfl.webSocket.dao.CompteDao;
import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonUserDetailsService  implements UserDetailsService {
    @Autowired
    private CompteDao compteDao;
    @Override
    public UserDetails loadUserByUsername(String nom) {
        Optional<Compte> compte = compteDao.findById(nom);
        Compte c;
        if (compte.isPresent())
            c = compte.get();
        else
        {
            //Mot de passe anonyme est JaimeLesPatates!!!
            c = new Compte("anonyme", "anonyme",
                    "$2a$10$v3EXvAFWCsyUNWmKaw6uI.TanoXeQ9h0zq4mxurfdSow.dvCvOovO", "");
        }
        return new MonUserPrincipal(c);
    }

}
