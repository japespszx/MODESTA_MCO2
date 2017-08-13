package ph.edu.dlsu.modesta.R;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 * Created by patricktobias on 06/08/2017.
 */
public class RUtils {

    private static RConnection rserve = Rserve.getConnection();

    public RUtils() {
        rserve = Rserve.getConnection();
    }

    public static double dmultinom (int[] x, double[] prob) {

        double result = 0;

        String input;

        input = "c(";

        for (int i = 0; i < x.length; i++) {
            input = input.concat(x[i]+"");

            if (i != x.length - 1)
                input = input.concat(",");
        }

        input = input.concat("), prob = c(");

        for (int i = 0; i < prob.length; i++) {
            input = input.concat(prob[i]+"");

            if (i != prob.length - 1)
                input = input.concat(",");
        }

        input = input.concat(")");

        System.out.println(input);

        try {
			rserve.eval("result=dmultinom(" + input + ")");
			result = rserve.eval("result").asDouble();
			System.out.println(result);
		} catch (RserveException | REXPMismatchException e) {
			e.printStackTrace();
		} finally {
			if (rserve != null)
				rserve.close();
		}

		return result;
    }
}