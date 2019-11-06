package cgg.informatique.jfl.webSocket.controleurs;



import cgg.informatique.jfl.webSocket.Message;
import cgg.informatique.jfl.webSocket.Reponse;
import cgg.informatique.jfl.webSocket.dao.CompteDao;
import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class ReponseControleur {
    ArrayList<String> spectateurs = new ArrayList<>();
    ArrayList<String> combattants = new ArrayList<>();
    ArrayList<String> arbitres = new ArrayList<>();
    static long id = 1;
    @Autowired
    CompteDao compteDao;

    @CrossOrigin()
    @MessageMapping("/message")
    @SendTo("/sujet/reponse")
    public Reponse reponse(Message message) throws Exception {
        System.err.println(message.toString());
        return new Reponse( id++, message.getDe(), message.getTexte() ,message.getCreation() , message.getAvatar());
    }
    @RequestMapping("/saveSpectateur/{id}")
    public ArrayList<String> saveSpec(@PathVariable String id) {
        Compte compte = compteDao.findById(id).get();
        if(combattants.contains(compte.getAvatar().getAvatar()))
            combattants.remove(compte.getAvatar().getAvatar());
        if(arbitres.contains(compte.getAvatar().getAvatar()))
            arbitres.remove(compte.getAvatar().getAvatar());
        if(!spectateurs.contains(compte.getAvatar().getAvatar()))
            spectateurs.add(compte.getAvatar().getAvatar());
        return spectateurs;
    }

    @RequestMapping("/saveCombattant/{id}")
    public ArrayList<String> saveComb(@PathVariable String id) {
        Compte compte = compteDao.findById(id).get();
        if(spectateurs.contains(compte.getAvatar().getAvatar()))
            spectateurs.remove(compte.getAvatar().getAvatar());
        if(arbitres.contains(compte.getAvatar().getAvatar()))
            arbitres.remove(compte.getAvatar().getAvatar());
        if(!combattants.contains(compte.getAvatar().getAvatar()))
            combattants.add(compte.getAvatar().getAvatar());
        return combattants;
    }

    @RequestMapping("/saveArbitre/{id}")
    public ArrayList<String> saveArb(@PathVariable String id) {
        Compte compte = compteDao.findById(id).get();
        if(spectateurs.contains(compte.getAvatar().getAvatar()))
            spectateurs.remove(compte.getAvatar().getAvatar());
        if(combattants.contains(compte.getAvatar().getAvatar()))
            combattants.remove(compte.getAvatar().getAvatar());
        if(!arbitres.contains(compte.getAvatar().getAvatar()))
            arbitres.add(compte.getAvatar().getAvatar());
        return arbitres;
    }

    @RequestMapping("/getSpectateurs}")
    public ArrayList<String> getSpec() {
        return spectateurs;
    }

    @RequestMapping("/getCombattants}")
    public ArrayList<String> getComb() {
        return combattants;
    }

    @RequestMapping("/getArbitres}")
    public ArrayList<String> getArb() {
        return arbitres;
    }

}
