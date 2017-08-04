package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Binomial {
	private static RConnection connection = Rserve.getConnection();

	public static double dbinom(int x, int size, double prob) {
		double val = 0;
		try {
			val = connection.
					eval("dbinom(" + x + "," + size + "," + prob + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double pbinom(int q, int size, double prob) {
		double val = 0;
		try {
			val = connection.
					eval("pbinom(" + q + "," + size + "," + prob + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double qbinom(int p, int size, double prob) {
		double val = 0;
		try {
			val = connection.
					eval("qbinom(" + p + "," + size + "," + prob + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double[] rbinom(int n, int size, double prob) {
		double[] val = new double[0];
		try {
			val = connection.
					eval("rbinom(" + n + "," + size + "," + prob + ")").
					asDoubles();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}
}
