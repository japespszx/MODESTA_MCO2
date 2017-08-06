package ph.edu.dlsu.modesta;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class MakeFrequencyMap {
	public static void main(String[] args) throws IOException {
		FileWriter w = new FileWriter("FrequencyMap.csv");
		ArrayList<String> values = new ArrayList<>();
		values.add("drawn");
		values.add("total");
		values.add("possible");
		CSVUtils.writeLine(w, values);

		for (int i = 1; i <= 13; i++) {
			values = new ArrayList<>();
			values.add("1");
			values.add(Integer.toString(i));
			values.add("1");
			CSVUtils.writeLine(w, values);
		}

		int drawn = 2;
		int[] possible = new int[27]; //possible[0] won't be used
		for (int i = 1; i < 14; i++) {
			for (int j = i; j < 14; j++) {
				for (int k = 1; k < 27; k++) {
					if (i + j == k)
						possible[k]++;
				}
			}
		}

		for (int i = 1; i < 27; i++) {
			values = new ArrayList<>();
			values.add(Integer.toString(drawn));
			values.add(Integer.toString(i));
			values.add(Integer.toString(possible[i]));
			CSVUtils.writeLine(w, values);
		}

		drawn = 3;
		possible = new int[40]; //possible[0] won't be used
		for (int i = 1; i <= 13; i++) {
			for (int j = i; j <= 13; j++) {
				for (int k = j; k <= 13; k++) {
					for (int l = 1; l < 40; l++) {
						if (i + j + k == l)
							possible[l]++;
					}
				}
			}
		}

		for (int i = 1; i < 40; i++) {
			values = new ArrayList<>();
			values.add(Integer.toString(drawn));
			values.add(Integer.toString(i));
			values.add(Integer.toString(possible[i]));
			CSVUtils.writeLine(w, values);
		}

		drawn = 4;
		possible = new int[53]; //possible[0] won't be used
		for (int i = 1; i <= 13; i++) {
			for (int j = i; j <= 13; j++) {
				for (int k = j; k <= 13; k++) {
					for (int l = k; l <= 13; l++) {
						for (int m = 1; m < 53; m++) {
							if (i + j + k + l == m)
								possible[m]++;
						}
					}
				}
			}
		}

		for (int i = 1; i < 53; i++) {
			values = new ArrayList<>();
			values.add(Integer.toString(drawn));
			values.add(Integer.toString(i));
			values.add(Integer.toString(possible[i]));
			CSVUtils.writeLine(w, values);
		}

		drawn = 5;
		possible = new int[65]; //possible[0] won't be used
		for (int i = 1; i <= 13; i++) {
			for (int j = i; j <= 13; j++) {
				for (int k = j; k <= 13; k++) {
					for (int l = k; l <= 13; l++) {
						for (int m = l; m <= 13; m++) {
							for (int n = 1; n < 65; n++) {
								if (isAllEqual(i, j, k, l, m))
									continue;

								if (i + j + k + l + m == n)
									possible[n]++;
							}
						}
					}
				}
			}
		}

		for (int i = 1; i < 65; i++) {
			values = new ArrayList<>();
			values.add(Integer.toString(drawn));
			values.add(Integer.toString(i));
			values.add(Integer.toString(possible[i]));
			CSVUtils.writeLine(w, values);
		}

		w.close();
	}

	private static boolean isAllEqual(int i, int j, int k, int l, int m) {
		return i == j && j == k && k == l && l == m;
	}
}
