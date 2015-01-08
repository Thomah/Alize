package alize.eole.thread;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import alize.eole.websocket.handler.WebsocketEndPoint;

@Component
public class Tasks {
	
	private static CalculTask calculTask;
	
	public static void setCalculTask(LocalDateTime l) {
		
		WebsocketEndPoint websocket = calculTask.getWebsocket();
		
		calculTask = new CalculTask();
		calculTask.setWebsocket(websocket);
		calculTask.setFinEole(l);
		calculTask.start();
	}
	
	public static CalculTask getCalculTask() {
		return calculTask;
	}
	
	public void setCalculTask(CalculTask calculTask) {
		Tasks.calculTask = calculTask;
	}
	
}
