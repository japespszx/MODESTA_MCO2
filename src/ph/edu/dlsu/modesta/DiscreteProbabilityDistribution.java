package ph.edu.dlsu.modesta;

public class DiscreteProbabilityDistribution {

	private int currentN;

	public int factorial(int n) {
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

	private int product(int n) {
		int m = n / 2;
		if (m == 0)
			return currentN += 2;
		else if (n == 2)
			return (currentN += 2) * (currentN += 2);
		else
			return product(n - m) * product(m);
	}

	public int permutation(int n, int r) {
		return factorial(n) / factorial(n - r);
	}

	public int combination(int n, int r) {
		return factorial(n) / (factorial(r) * factorial(n - r));
	}

	public double dbinom(int x, int n, double p) {
		return combination(n, x) * Math.pow(p, x) * Math.pow(1 - p, n - x);
	}

	public double pbinom(int x, int n, double p) {
		double val = 0;
		for (int i = 0; i <= x; i++) {
			val += dbinom(x, n, p);
		}
		return val;
	}
}
