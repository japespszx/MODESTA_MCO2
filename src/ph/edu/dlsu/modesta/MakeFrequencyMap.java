package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;
import ph.edu.dlsu.modesta.R.Rserve;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

class MakeFrequencyMap {
	public static void main(String[] args) throws IOException, RserveException, REXPMismatchException {
		int[] draw1 = new int[14];
		int[] draw2 = new int[27];
		int[] draw3 = new int[40];
		int[] draw4 = new int[53];
		int[] draw5 = new int[65];

		for (int i = 1; i <= 5; i++) {
			boolean toggle;
			for (int j = 1; j <= 52; j++) { //1st draw
				int drawtotal1;
				if (j % 13 == 0)
					drawtotal1 = 13;
				else
					drawtotal1 = j % 13;
				toggle = false;
				if (i == 1) {
					draw1[drawtotal1]++;
					toggle = true;
				}
				if (!toggle) {
					for (int k = j + 1; k <= 52; k++) { //2nd draw
						int drawtotal2 = drawtotal1;
						if (k % 13 == 0)
							drawtotal2 += 13;
						else
							drawtotal2 += k % 13;
						toggle = false;
						if (i == 2) {
							draw2[drawtotal2]++;
							toggle = true;
						}
						if (!toggle) {
							for (int l = k + 1; l <= 52; l++) { //3rd draw
								int drawtotal3 = drawtotal2;
								if (l % 13 == 0)
									drawtotal3 += 13;
								else
									drawtotal3 += l % 13;
								toggle = false;
								if (i == 3) {
									draw3[drawtotal3]++;
									toggle = true;
								}
								if (!toggle) {
									for (int m = l + 1; m <= 52; m++) { //4th draw
										int drawtotal4 = drawtotal3;
										if (m % 13 == 0)
											drawtotal4 += 13;
										else
											drawtotal4 += m % 13;
										toggle = false;
										if (i == 4) {
											draw4[drawtotal4]++;
											toggle = true;
										}
										if (!toggle) {
											for (int n = m + 1; n <= 52; n++) { //5th draw
												int drawtotal5 = drawtotal4;
												if (!(n == j || n == k || n == l || n == m)) {
													if (n % 13 == 0)
														drawtotal5 += 13;
													else
														drawtotal5 += n % 13;
													draw5[drawtotal5]++;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		double divisor1 = combination(52, 1);
		double divisor2 = combination(52, 2);
		double divisor3 = combination(52, 3);
		double divisor4 = combination(52, 4);
		double divisor5 = combination(52, 5);

		System.out.println("1 CARDS DRAWN");
		FileWriter w = new FileWriter("fwor1.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 13; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw1[i] / divisor1), w);
		}
		w.close();

		System.out.println("2 CARDS DRAWN");
		w = new FileWriter("fwor2.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 26; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw2[i] / divisor2), w);
		}
		w.close();

		System.out.println("3 CARDS DRAWN");
		w = new FileWriter("fwor3.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 39; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw3[i] / divisor3), w);
		}
		w.close();

		System.out.println("4 CARDS DRAWN");
		w = new FileWriter("fwor4.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 52; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw4[i] / divisor4), w);
		}
		w.close();

		System.out.println("5 CARDS DRAWN");
		w = new FileWriter("fwor5.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 64; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw5[i] / divisor5), w);
		}
		w.close();

		draw1 = new int[14];
		draw2 = new int[27];
		draw3 = new int[40];
		draw4 = new int[53];
		draw5 = new int[66];

		for (int i = 1; i <= 5; i++) {
			boolean toggle;
			for (int j = 1; j <= 52; j++) { //1st draw
				int drawtotal1;
				if (j % 13 == 0)
					drawtotal1 = 13;
				else
					drawtotal1 = j % 13;
				toggle = false;
				if (i == 1) {
					draw1[drawtotal1]++;
					toggle = true;
				}
				if (!toggle) {
					for (int k = 1; k <= 52; k++) { //2nd draw
						int drawtotal2 = drawtotal1;
						if (k % 13 == 0)
							drawtotal2 += 13;
						else
							drawtotal2 += k % 13;
						toggle = false;
						if (i == 2) {
							draw2[drawtotal2]++;
							toggle = true;
						}
						if (!toggle) {
							for (int l = 1; l <= 52; l++) { //3rd draw
								int drawtotal3 = drawtotal2;
								if (l % 13 == 0)
									drawtotal3 += 13;
								else
									drawtotal3 += l % 13;
								toggle = false;
								if (i == 3) {
									draw3[drawtotal3]++;
									toggle = true;
								}
								if (!toggle) {
									for (int m = 1; m <= 52; m++) { //4th draw
										int drawtotal4 = drawtotal3;
										if (m % 13 == 0)
											drawtotal4 += 13;
										else
											drawtotal4 += m % 13;
										toggle = false;
										if (i == 4) {
											draw4[drawtotal4]++;
											toggle = true;
										}
										if (!toggle) {
											for (int n = 1; n <= 52; n++) { //5th draw
												int drawtotal5 = drawtotal4;
												if (n % 13 == 0)
													drawtotal5 += 13;
												else
													drawtotal5 += n % 13;
												draw5[drawtotal5]++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		divisor1 = Math.pow(52, 1);
		divisor2 = Math.pow(52, 2);
		divisor3 = Math.pow(52, 3);
		divisor4 = Math.pow(52, 4);
		divisor5 = Math.pow(52, 5);

		System.out.println("1 CARDS DRAWN");
		w = new FileWriter("fwr1.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 13; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw1[i] / divisor1), w);
		}
		w.close();

		System.out.println("2 CARDS DRAWN");
		w = new FileWriter("fwr2.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 26; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw2[i] / divisor2), w);
		}
		w.close();

		System.out.println("3 CARDS DRAWN");
		w = new FileWriter("fwr3.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 39; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw3[i] / divisor3), w);
		}
		w.close();

		System.out.println("4 CARDS DRAWN");
		w = new FileWriter("fwr4.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 52; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw4[i] / divisor4), w);
		}
		w.close();

		System.out.println("5 CARDS DRAWN");
		w = new FileWriter("fwr5.csv");
		writeToCSV("Total", "Possible", w);
		for (int i = 1; i <= 65; i++) {
			writeToCSV(i, BigDecimal.valueOf(draw5[i] / divisor5), w);
		}
		w.close();
	}

	private static void writeToCSV(String s1, String s2, FileWriter w) {
		ArrayList<String> values = new ArrayList<>();
		values.add(s1);
		values.add(s2);
		try {
			CSVUtils.writeLine(w, values);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeToCSV(int i, BigDecimal possible, FileWriter w) {
		ArrayList<String> values = new ArrayList<>();
		values.add(Integer.toString(i));
		values.add(possible.toString());
		try {
			CSVUtils.writeLine(w, values);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int combination(int n, int r) throws RserveException, REXPMismatchException {
		return Rserve.getConnection().eval("factorial(" + n +
				")/(factorial(" + r +
				")*factorial(" + n + "-" + r + "))").asInteger();
	}
}
