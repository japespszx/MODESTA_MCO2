package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Poisson {
	private static RConnection connection = Rserve.getConnection();

	public static double dpois(int x, double lambda) {
		double val = 0;
		try {
			val = connection.
					eval("dpois(" + x + "," + lambda + "," + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double ppois(int q, double lambda) {
		double val = 0;
		try {
			val = connection.
					eval("ppois(" + q + "," + lambda + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double qpois(double p, double lambda) {
		double val = 0;
		try {
			val = connection.
					eval("qpois(" + p + "," + lambda + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double[] rpois(int n, double lambda) {
		double[] val = new double[0];
		try {
			val = connection.
					eval("rpois(" + n + "," + lambda + ")").
					asDoubles();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}
}
