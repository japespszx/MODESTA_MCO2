package ph.edu.dlsu.modesta;

import java.math.BigDecimal;

public class DiscreteProbabilityDistribution {

	static private int currentN;

	static public int factorial(int n) {
		if (n < 0) {
			try {
				throw new Exception("n < 0");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (n < 2)
			return 1;

		int p = 1;
		int r = 1;
		currentN = 1;

		int h = 0, shift = 0, high = 1;
		int log2n = (int) (Math.log(n) / Math.log(2));

		while (h != n) {
			shift += h;
			h = n >> log2n--;
			int len = high;
			high = (h - 1) | 1;
			len = (high - len) / 2;

			if (len > 0) {
				p *= product(len);
				r *= p;
			}
		}

		return r << shift;
	}

	static private int product(int n) {
		int m = n / 2;
		if (m == 0)
			return currentN += 2;
		else if (n == 2)
			return (currentN += 2) * (currentN += 2);
		else
			return product(n - m) * product(m);
	}

	static public int permutation(int n, int r) {
		return factorial(n) / factorial(n - r);
	}

	static public int combination(int n, int r) {
		return factorial(n) / (factorial(r) * factorial(n - r));
	}

	static public BigDecimal dbinom(int x, int n, double p) {
		BigDecimal temp = BigDecimal.valueOf(p);
		return new BigDecimal(combination(n, x)).
				multiply(temp.pow(x)).
				multiply(BigDecimal.ONE.subtract(temp).pow(n - x));
	}

	static public BigDecimal pbinom(int x, int n, double p) {
		BigDecimal val = new BigDecimal(0);
		for (int i = 0; i <= x; i++) {
			val = val.add(dbinom(i, n, p));
		}
		return val;
	}
}
