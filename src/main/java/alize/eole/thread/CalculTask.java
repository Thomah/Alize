package alize.eole.thread;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.web.socket.TextMessage;

import alize.eole.websocket.handler.WebsocketEndPoint;

public class CalculTask extends Thread {

	private WebsocketEndPoint websocket;
	private LocalDateTime debutEole;
	private LocalDateTime finEole;
	
	private String journal = "";
	
	@Override
	public void run() {
		
		setDebutEole(LocalDateTime.now());
			while(LocalDateTime.now().isBefore(finEole)) {

				ZonedDateTime zdtDebut = Tasks.getCalculTask().getDebutEole().atZone(ZoneId.systemDefault());
				ZonedDateTime zdtFin = Tasks.getCalculTask().getFinEole().atZone(ZoneId.systemDefault());
				double percent = (((double)(new Date()).getTime() - (double)zdtDebut.toInstant().toEpochMilli()) / ((double)zdtFin.toInstant().toEpochMilli() - (double)zdtDebut.toInstant().toEpochMilli())) * 100;
				
				ajouterLigneLog("Une sec de plus !");
				String jSon = "{'avancement':'" + Double.toString(percent) + "','journal':'" + journal + "'}";
				jSon = jSon.replace('\'', '\"');
				websocket.sendMessage(new TextMessage(jSon));
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
	}
	
	public void ajouterLigneLog(String message) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		journal+="<p>[" + LocalDateTime.now().format(formatter) + "] " + message + "</p>";
	}
	
	public WebsocketEndPoint getWebsocket() {
		return websocket;
	}
	
	public void setWebsocket(WebsocketEndPoint websocket) {
		this.websocket = websocket;
	}
	
	public LocalDateTime getDebutEole() {
		return debutEole;
	}

	private void setDebutEole(LocalDateTime debutEole) {
		this.debutEole = debutEole;
	}

	public LocalDateTime getFinEole() {
		return finEole;
	}

	public void setFinEole(LocalDateTime finEole) {
		this.finEole = finEole;
	}

}
