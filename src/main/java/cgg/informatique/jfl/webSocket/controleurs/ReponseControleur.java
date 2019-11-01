package cgg.informatique.jfl.webSocket.controleurs;



import cgg.informatique.jfl.webSocket.Message;
import cgg.informatique.jfl.webSocket.Reponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

@RestController
public class ReponseControleur {

    static long id = 1;

    @CrossOrigin()
    @MessageMapping("/message")
    @SendTo("/sujet/reponse")
    public Reponse reponse(Message message) throws Exception {
        System.err.println(message.toString());
        return new Reponse( id++, message.getDe(), message.getTexte() ,message.getCreation() , message.getAvatar());
    }

}
