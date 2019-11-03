package cgg.informatique.jfl.webSocket.controleurs;



import cgg.informatique.jfl.webSocket.Message;
import cgg.informatique.jfl.webSocket.Reponse;
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
    static long id = 1;

    @CrossOrigin()
    @MessageMapping("/message")
    @SendTo("/sujet/reponse")
    public Reponse reponse(Message message) throws Exception {
        System.err.println(message.toString());
        return new Reponse( id++, message.getDe(), message.getTexte() ,message.getCreation() , message.getAvatar());
    }
    @CrossOrigin()
    @MessageMapping("/saveSpectateur")
    public void racine(@RequestBody String name) {
        spectateurs.add(name);
    }

}
