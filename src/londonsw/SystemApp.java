	package londonsw;

	import londonsw.controller.StartUpController;

/**
 * This is the main class that first gets executed by the system
 * It starts the View components (and from there will branch to either simulation or map-making)
 */
public class SystemApp {
	public static void main(String[] args) throws Exception {
		StartUpController sc = new StartUpController();
		sc.startSoftware(args);
	}
}