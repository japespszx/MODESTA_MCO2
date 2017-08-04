package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Hypergeometric {
	private static RConnection connection = Rserve.getConnection();

	public static double dhyper(int x, int m, int n, int k) {
		double val = 0;
		try {
			val = connection.
					eval("dhyper(" + x + "," + m + "," + n + "," + k + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double phyper(int q, int m, int n, int k) {
		double val = 0;
		try {
			val = connection.
					eval("phyper(" + q + "," + m + "," + n + "," + k + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double qhyper(double p, int m, int n, int k) {
		double val = 0;
		try {
			val = connection.
					eval("qhyper(" + p + "," + m + "," + n + "," + k + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double[] rhyper(int nn, int m, int n, int k) {
		double[] val = new double[0];
		try {
			val = connection.
					eval("rhyper(" + nn + "," + m + "," + n + "," + k + ")").
					asDoubles();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}
}
