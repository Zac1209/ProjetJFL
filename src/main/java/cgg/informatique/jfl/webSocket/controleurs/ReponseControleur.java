package cgg.informatique.jfl.webSocket.controleurs;



import cgg.informatique.jfl.webSocket.Message;
import cgg.informatique.jfl.webSocket.Reponse;
import cgg.informatique.jfl.webSocket.dao.CompteDao;
import cgg.informatique.jfl.webSocket.entite.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ReponseControleur {
    ArrayList<String> spectateurs = new ArrayList<>();
    ArrayList<String> competiteurs = new ArrayList<>();
    ArrayList<String> arbitres = new ArrayList<>();
    HashMap<String,String> positionCombattantDroite = new HashMap<>();//Key = avatar, value = position
    HashMap<String,String> positionCombattantGauche = new HashMap<>();
    String arbitreActuel = "";
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
        if(competiteurs.contains(compte.getAvatar().getAvatar()))
            competiteurs.remove(compte.getAvatar().getAvatar());
        if(arbitres.contains(compte.getAvatar().getAvatar()))
            arbitres.remove(compte.getAvatar().getAvatar());
        if(!spectateurs.contains(compte.getAvatar().getAvatar()))
            spectateurs.add(compte.getAvatar().getAvatar());
        return spectateurs;
    }

    @RequestMapping("/saveCompetiteur/{id}")
    public ArrayList<String> saveComb(@PathVariable String id) {
        Compte compte = compteDao.findById(id).get();
        if(spectateurs.contains(compte.getAvatar().getAvatar()))
            spectateurs.remove(compte.getAvatar().getAvatar());
        if(arbitres.contains(compte.getAvatar().getAvatar()))
            arbitres.remove(compte.getAvatar().getAvatar());
        if(!competiteurs.contains(compte.getAvatar().getAvatar()))
            competiteurs.add(compte.getAvatar().getAvatar());
        return competiteurs;
    }

    @RequestMapping("/saveArbitre/{id}")
    public ArrayList<String> saveArb(@PathVariable String id) {
        Compte compte = compteDao.findById(id).get();
        if(spectateurs.contains(compte.getAvatar().getAvatar()))
            spectateurs.remove(compte.getAvatar().getAvatar());
        if(competiteurs.contains(compte.getAvatar().getAvatar()))
            competiteurs.remove(compte.getAvatar().getAvatar());
        if(!arbitres.contains(compte.getAvatar().getAvatar()))
            arbitres.add(compte.getAvatar().getAvatar());
        return arbitres;
    }

    @GetMapping("/getSpectateurs")
    public ArrayList<String> getSpec() {
        return spectateurs;
    }

    @GetMapping("/getCompetiteurs")
    public ArrayList<String> getComb() {
        return competiteurs;
    }

    @GetMapping("/getArbitres")
    public ArrayList<String> getArb() {
        return arbitres;
    }

    @RequestMapping("/saveCombatState/{arbitreID}/{combDroitID}/{combDroitPos}/{combGaucheID}/{combGauchePos}")
    public void saveCombatState(@PathVariable String arbitreID,
                                             @PathVariable String combDroitID,
                                             @PathVariable String combDroitPos,
                                             @PathVariable String combGaucheID,
                                             @PathVariable String combGauchePos) {
        Compte combattantDroit = compteDao.findById(combDroitID).get();
        Compte combattantGauche = compteDao.findById(combGaucheID).get();
        positionCombattantDroite.put(combattantDroit.getAvatar().getAvatar(),combDroitPos);
        positionCombattantGauche.put(combattantGauche.getAvatar().getAvatar(),combGauchePos);
        arbitreActuel = compteDao.findById(arbitreID).get().getAvatar().getAvatar();
    }

    @PostMapping("/getCompteByAvatar")
    public String getCompteByAvatar(@RequestBody String test) {
        String avatar = compteDao.getCompteIdByAvatar(test);
        return avatar;
    }

}
