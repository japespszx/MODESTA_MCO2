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

		for (int i = 1; i <= 26; i++) {
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

		for (int i = 1; i <= 39; i++) {
			values = new ArrayList<>();
			values.add(Integer.toString(drawn));
			values.add(Integer.toString(i));
			values.add(Integer.toString(possible[i]));
			CSVUtils.writeLine(w, values);
		}

		drawn = 4;
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

		for (int i = 1; i <= 39; i++) {
			values = new ArrayList<>();
			values.add(Integer.toString(drawn));
			values.add(Integer.toString(i));
			values.add(Integer.toString(possible[i]));
			CSVUtils.writeLine(w, values);
		}

		w.close();
	}
}
