package alize.eole.thread;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CalculTask implements Runnable {

	private LocalDateTime debutEole;
	private LocalDateTime finEole;
	
	public CalculTask(LocalDateTime finEole) {
		this.setFinEole(finEole);
	}
	
	@Override
	public void run() {
		
		setDebutEole(LocalDateTime.now());
		while(LocalDateTime.now().isBefore(finEole)) {
			
		}

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

	private void setFinEole(LocalDateTime finEole) {
		this.finEole = finEole;
	}

}
