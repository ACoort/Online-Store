package resources;

/**
 * The runnable
 *
 * @author Ayden Coort and Edwin Gonzalez Dos Santos
 * @date 2017-12-04
 */

public class AppRunnable implements Runnable {

	public void run() {
		createAndShowGUI();
	}

	public static void createAndShowGUI() {
		Model model = new Model();
		ViewController viewController = new ViewController(model);
		model.addObserver(viewController);
	}
}
