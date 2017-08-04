package ph.edu.dlsu.modesta;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Rserve {

	private static RConnection connection = null;

	public static RConnection getConnection() {
		if (connection == null) {
			try {
				connection = new RConnection();

			} catch (RserveException e) {
				e.printStackTrace();
			}
		}

		return connection;
	}
}
