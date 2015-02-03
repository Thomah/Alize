package alize.eole.thread;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import alize.commun.service.StockageService;
import alize.eole.websocket.handler.WebsocketEndPoint;

@Component
public class Tasks {
	
	private static TimerTask timerTask;
	
	public static void setTimerTask(int nombreIterations) {

		StockageService stockageService = timerTask.getStockageService();
		WebsocketEndPoint websocket = timerTask.getWebsocket();
		
		timerTask = new TimerTask();
		timerTask.setStockageService(stockageService);
		timerTask.setWebsocket(websocket);
		timerTask.setNombreIterations(nombreIterations);
		timerTask.start();
	}
	
	public static TimerTask getTimerTask() {
		return timerTask;
	}
	
	public void setTimerTask(TimerTask timerTask) {
		Tasks.timerTask = timerTask;
	}
	}
