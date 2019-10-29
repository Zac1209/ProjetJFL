package cgg.informatique.jfl.webSocket.controleurs;

import cgg.informatique.jfl.webSocket.dao.CompteDao;
import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.Optional;

@Controller
public class controleurMVC {
    @Autowired
    private CompteDao compteDao;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String racine(Model model) {
        Compte karateka = new Compte();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        String courriel = auth.getName();
        Optional<Compte> compte = compteDao.findById(courriel);

        model.addAttribute("nom", "test123");

        return "dojo";
    }
    @RequestMapping(value = "/dojo", method = RequestMethod.GET)
    public String dojo(Map<String, Object> model) {
        return "dojo";
    }
    @RequestMapping(value = "/notreEcole", method = RequestMethod.GET)
    public String ecole(Map<String, Object> model) {
        return "notreEcole";
    }
    @RequestMapping(value = "/kumite", method = RequestMethod.GET)
    public String kumite(Map<String, Object> model) {
        return "kumite";
    }
    @RequestMapping(value = "/passageGrade", method = RequestMethod.GET)
    public String passage(Map<String, Object> model) {
        return "passageGrade";
    }
}

