package cgg.informatique.jfl.webSocket.controleurs;

import cgg.informatique.jfl.webSocket.dao.CompteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class controleurMVC {
    @Autowired
    private CompteDao compteDao;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String racine(Map<String, Object> model) {
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

