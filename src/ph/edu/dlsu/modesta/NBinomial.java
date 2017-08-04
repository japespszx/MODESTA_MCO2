package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class NBinomial {
	private static RConnection connection = Rserve.getConnection();

	public static double dnbinom(int x, int size, double prob) {
		double val = 0;
		try {
			val = connection.
					eval("dnbinom(" + x + "," + size + "," + prob + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double pnbinom(int q, int size, double prob) {
		double val = 0;
		try {
			val = connection.
					eval("pnbinom(" + q + "," + size + "," + prob + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double qnbinom(double p, int size, double prob) {
		double val = 0;
		try {
			val = connection.
					eval("qnbinom(" + p + "," + size + "," + prob + ")").
					asDouble();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}

	public static double[] rnbinom(int n, int size, double prob) {
		double[] val = new double[0];
		try {
			val = connection.
					eval("rnbinom(" + n + "," + size + "," + prob + ")").
					asDoubles();
			System.out.println();
		} catch (REXPMismatchException | RserveException e) {
			e.printStackTrace();
		}

		return val;
	}
}
