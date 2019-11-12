package cgg.informatique.jfl.webSocket.controleurs;


import cgg.informatique.jfl.webSocket.Message;
import cgg.informatique.jfl.webSocket.Reponse;
import cgg.informatique.jfl.webSocket.WebSocketApplication;
import cgg.informatique.jfl.webSocket.dao.CombatDao;
import cgg.informatique.jfl.webSocket.dao.CompteDao;
import cgg.informatique.jfl.webSocket.entite.Combat;
import cgg.informatique.jfl.webSocket.entite.Compte;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ReponseControleur {
    ArrayList<String> spectateurs = new ArrayList<>();
    ArrayList<String> competiteurs = new ArrayList<>();
    ArrayList<String> arbitres = new ArrayList<>();
    HashMap<String,String> positionCombattant = new HashMap<>();//Key = avatar, value = position
    String arbitreActuel = "";
    String arbitreTemp = "";
    int arbitreActuelCount = 0;
    HashMap<String,String> resultCombat = new HashMap<>();//Key = action faite, value = avatar
    Compte combattantDroit;
    Compte combattantGauche;

    static long id = 1;
    Thread timer;
    @Autowired
    CompteDao compteDao;
    @Autowired
    CombatDao combatDao;

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

        combattantDroit = compteDao.findById(combDroitID).get();
        combattantGauche = compteDao.findById(combGaucheID).get();
        competiteurs.remove(combattantDroit.getAvatar().getAvatar());
        competiteurs.remove(combattantGauche.getAvatar().getAvatar());
        positionCombattant.put(combattantDroit.getAvatar().getAvatar(),combDroitPos);
        positionCombattant.put(combattantGauche.getAvatar().getAvatar(),combGauchePos);
        arbitreActuel = compteDao.findById(arbitreID).get().getAvatar().getAvatar();

        if(combDroitPos.equals("9")){//Commencer timer 3 secondes pour action
            timer = new Thread(new Runnable() {
                public void run(){
                    try {
                        Thread.sleep(3000);
                        if(resultCombat.size() != 2){
                            String positionDrapeauGagnant = "";
                            if(resultCombat.size() == 0)
                                positionDrapeauGagnant = "egal";
                            else{
                                String avatarQuiAJouer = new ArrayList<String>(resultCombat.values()).get(0);
                                String positionDeCeluiQuiAJouer = positionCombattant.get(avatarQuiAJouer);

                                if(positionDeCeluiQuiAJouer.equals("9"))
                                    positionDrapeauGagnant = "7";
                                else
                                    positionDrapeauGagnant = "5";

                            }
                            WebSocketApplication.session.send("/sujet/resultatCombat", new Message(positionDrapeauGagnant,"",""));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            timer.start();
        }
    }

    @PostMapping("/getCompteByAvatar")
    public String getCompteByAvatar(@RequestBody String test) {
        String avatar = compteDao.getCompteIdByAvatar(test);
        return avatar;
    }

    @GetMapping("/getCombattant")
    public String getCombatState() {
        String d = getJSONFromMap(positionCombattant);
        return getJSONFromMap(positionCombattant);
    }

    @GetMapping("/getArbitreActuel")
    public String getArbitreActuel() {
        return arbitreActuel;
    }

    @RequestMapping("/saveCombatResult/{combattantID}/{result}")
    public String saveCombatResult(@PathVariable String combattantID,
                                 @PathVariable String result) {
        String gagnantPosition = "";
        Compte combattant = compteDao.findById(combattantID).get();
        resultCombat.put(result,combattant.getAvatar().getAvatar());
        if(resultCombat.size() == 2){//Si on a 2 résultat, calculer le gagnant.
            ArrayList<String> resultList = new ArrayList<>();
            for ( String key : resultCombat.keySet() ) {
                resultList.add(key);
            }
            if(resultList.get(0).equals(resultList.get(1)))
                gagnantPosition = "egal";
            else{
                if(resultList.get(0).equals("Roche")){
                    if(resultList.get(1).equals("Papier"))
                        gagnantPosition = resultCombat.get(resultList.get(1));
                    else
                        gagnantPosition = resultCombat.get(resultList.get(0));
                }else if(resultList.get(0).equals("Papier")){
                    if(resultList.get(1).equals("Ciseau"))
                        gagnantPosition = resultCombat.get(resultList.get(1));
                    else
                        gagnantPosition = resultCombat.get(resultList.get(0));
                }else if(resultList.get(0).equals("Ciseau")){
                    if(resultList.get(1).equals("Roche"))
                        gagnantPosition = resultCombat.get(resultList.get(1));
                    else
                        gagnantPosition = resultCombat.get(resultList.get(0));
                }
                gagnantPosition = positionCombattant.get(gagnantPosition);
                if(gagnantPosition.equals("3"))
                    gagnantPosition = "5";
                else
                    gagnantPosition = "7";
                new Thread(new Runnable() {
                    public void run(){
                        try {
                            Thread.sleep(10000);
                            positionCombattant = new HashMap<>();
                            resultCombat = new HashMap<>();
                            combattantDroit = null;
                            combattantGauche = null;
                            if(!arbitreTemp.equals("")){
                                arbitreTemp = "";
                            }{
                                arbitreActuel = "";
                            }
                            WebSocketApplication.session.send("/sujet/resetCombat", new Message());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
            int pointsBlanc = 0;
            int pointsRouge = 0;
            if(gagnantPosition.equals("egal")){
                pointsBlanc = 5;
                pointsRouge = 5;
            } else if (gagnantPosition.equals("5")){
                pointsBlanc = 10;
            }
            else
                pointsRouge = 10;

            Combat combat = new Combat(System.currentTimeMillis(),
                    compteDao.findById(getCompteByAvatar(arbitreActuel)).get(),
                    combattantDroit,
                    combattantGauche,
                    combattantDroit.getGroupe(),
                    combattantGauche.getGroupe(),
                    0,pointsBlanc,pointsRouge);
            combatDao.save(combat);
        }

        return gagnantPosition;//Retourse l'endroit où mettre le drapeau ou égal
    }

    @RequestMapping("/arbitreResterEnPlace/{id}")
    public String arbitreResterEnPlace(@PathVariable String id) {
        arbitreActuelCount++;
        String returnString = "";
        if(arbitreActuelCount <= 5){//Ne peut plus être arbitre
            returnString = arbitreActuel;
            arbitreTemp = arbitreActuel;
        }
        return returnString;
    }

    @RequestMapping("/exit/{id}")
    public void exit(@PathVariable String id) {
        Compte compte = compteDao.findById(id).get();
        if(spectateurs.contains(compte.getAvatar().getAvatar()))
            spectateurs.remove(compte.getAvatar().getAvatar());
        if(competiteurs.contains(compte.getAvatar().getAvatar()))
            competiteurs.remove(compte.getAvatar().getAvatar());
        if(arbitres.contains(compte.getAvatar().getAvatar())) {
            arbitres.remove(compte.getAvatar().getAvatar());
            if(arbitreActuel.equals(compte.getAvatar().getAvatar()))
                arbitreActuel = "";
        }
        WebSocketApplication.session.send("/sujet/positionUpdate", new Message());
    }

    public static String getJSONFromMap(Map<String, String> inputMap) {
        Writer writer = new StringWriter();
        JsonGenerator jsonGenerator;
        String outputResponse = null;
        try {
            jsonGenerator = new JsonFactory().createJsonGenerator(writer);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(jsonGenerator, inputMap);
            jsonGenerator.close();
            outputResponse = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputResponse;
    }

}
