package ph.edu.dlsu.modesta.R;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 * Created by patricktobias on 06/08/2017.
 */
public class RUtils {

    private static RConnection rserve = Rserve.getConnection();

    public static double dbinom(int x, int size, double prob) {

        double result = 0;
        String s = "result=dbinom(";

        s = s.concat(x + ",");
        s = s.concat(size + ",");
        s = s.concat(prob + ")");

        try {
            rserve.eval(s);
            result = rserve.eval("result").asDouble();
            //System.out.println(result);
        } catch (RserveException | REXPMismatchException e) {
            e.printStackTrace();
        }

        return result;

    }

    public static double dnbinom(int x, int size, double prob) {

        double result = 0;
        String s = "result=dnbinom(";

        s = s.concat(x + ",");
        s = s.concat(size + ",");
        s = s.concat(prob + ")");

        try {
            rserve.eval(s);
            result = rserve.eval("result").asDouble();
            //System.out.println(result);
        } catch (RserveException | REXPMismatchException e) {
            e.printStackTrace();
        }

        return result;

    }

    public static double dhyper(int x, int m, int n, int k) {

        double result = 0;
        String s = "result=dhyper(";

        s = s.concat(x+",");
        s = s.concat(m+",");
        s = s.concat(n+",");
        s = s.concat(k+")");

        try {
            rserve.eval(s);
            result = rserve.eval("result").asDouble();
            //System.out.println(result);
        } catch (RserveException | REXPMismatchException e) {
            e.printStackTrace();
        }

        return result;

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

        //System.out.println(input);

        try {
			rserve.eval("result=dmultinom(" + input + ")");
			result = rserve.eval("result").asDouble();
			//System.out.println(result);
		} catch (RserveException | REXPMismatchException e) {
			e.printStackTrace();
		} finally {
			if (rserve != null)
				rserve.close();
		}

		return result;
    }
}
