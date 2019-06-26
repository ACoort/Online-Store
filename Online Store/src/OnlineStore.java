import resources.AppRunnable;

/**
 * The main method that is to be executed for the program to operate
 *
 * @author Ayden Coort and Edwin Gonzalez Dos Santos
 * @date 2017-12-04
 */
public class OnlineStore {

	public static void main(String[] args) {
		Runnable application = new AppRunnable();
		javax.swing.SwingUtilities.invokeLater(application);
	}
}
