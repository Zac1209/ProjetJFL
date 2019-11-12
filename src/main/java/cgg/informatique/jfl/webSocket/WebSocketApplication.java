package cgg.informatique.jfl.webSocket;

import cgg.informatique.jfl.webSocket.configurations.MonStompSessionHandler;
import cgg.informatique.jfl.webSocket.configurations.WebSocketConfig;
import cgg.informatique.jfl.webSocket.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class WebSocketApplication implements CommandLineRunner {
	@Autowired
	private AvatarDao avatarDao;
	@Autowired
	private CombatDao combatDao;
	@Autowired
	private CompteDao compteDao;
	@Autowired
	private ExamenDao examenDao;
	@Autowired
	private GroupeDao groupeDao;
	@Autowired
	private RoleDao roleDao;
	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
	}
	@Autowired
	private WebSocketConfig webSocket;
	static public StompSession session;

	@Override
	public void run(String... args) throws Exception {

		WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>();


		transports.add(new WebSocketTransport(simpleWebSocketClient));
		SockJsClient sockJsClient = new SockJsClient(transports);

		//Créer un client Stomp qui gère les messages.
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		MappingJackson2MessageConverter conversionJson = new MappingJackson2MessageConverter();
		stompClient.setMessageConverter(conversionJson);
		stompClient.setInboundMessageSizeLimit(32 * 1024);

		//Se connecter à un websocket
		String url = "ws://localhost:8100/webSocket";
		StompSessionHandler sessionHandler = new MonStompSessionHandler();
		session = stompClient.connect(url, sessionHandler).get();



	}

}
