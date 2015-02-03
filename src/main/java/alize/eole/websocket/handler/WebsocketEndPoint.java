package alize.eole.websocket.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import alize.eole.thread.Tasks;

@Component
public class WebsocketEndPoint extends TextWebSocketHandler {

	private static final ArrayList<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws IOException {
		sessions.add(session);
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		String messagePayLoad = message.getPayload();
		JSONObject jObject = new JSONObject(messagePayLoad);
		
		String finEole = jObject.getString("finEole");
		int nombreIterations = Integer.valueOf(finEole);
		Tasks.setTimerTask(nombreIterations);
	}

	public void sendMessage(TextMessage textMessage) {
		for(WebSocketSession ws : sessions) {
			try {
				if(ws.isOpen()) {
					ws.sendMessage(textMessage);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
