package vacuum;

public class CleanSweep {
	
	private static CleanSweep instance;
	
	private CleanSweep() {}
	
	public static CleanSweep getInstance() {
		if (instance == null) {
			instance = new CleanSweep();
		}
		
		return instance;
	}
}
