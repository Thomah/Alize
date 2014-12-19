package alize.eole.thread;
import java.time.LocalDateTime;

public class Tasks {
	
	private static CalculTask calculs = null;
	
	public static void setCalculTask(LocalDateTime l) {
		if(calculs == null) {
			calculs = new CalculTask(l);
			calculs.run();
		}
	}
	
	public static CalculTask getCalculTask() {
		return calculs;
	}
	
}
