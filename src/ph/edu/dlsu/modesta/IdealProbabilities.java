package ph.edu.dlsu.modesta;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;
import ph.edu.dlsu.modesta.R.Rserve;

import java.math.BigDecimal;
import java.math.MathContext;

public class IdealProbabilities {
	public static BigDecimal[] get(boolean isFwr, int handSize) throws REXPMismatchException, RserveException {
		BigDecimal[] idealProbabilities;

		int[] draw1;
		int[] draw2;
		int[] draw3;
		int[] draw4;
		int[] draw5;
		int total1;
		int total2;
		int total3;
		int total4;
		int total5;

		BigDecimal divisor;

		if (!isFwr) {
			draw1 = new int[14];
			draw2 = new int[27];
			draw3 = new int[40];
			draw4 = new int[53];
			draw5 = new int[65];
			total1 = 0;
			total2 = 0;
			total3 = 0;
			total4 = 0;
			total5 = 0;

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
						total1++;
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
								total2++;
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
										total3++;
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
												total4++;
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
														total5++;
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

			divisor = new BigDecimal(combination(52, handSize));
		} else {
			draw1 = new int[14];
			draw2 = new int[27];
			draw3 = new int[40];
			draw4 = new int[53];
			draw5 = new int[66];
			total1 = 0;
			total2 = 0;
			total3 = 0;
			total4 = 0;
			total5 = 0;

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
						total1++;
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
								total2++;
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
										total3++;
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
												total4++;
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
													total5++;
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

			divisor = new BigDecimal(Math.pow(52, handSize));
		}

		int[] draw = new int[0];

		switch (handSize) {
			case 1:
				draw = draw1;
				break;
			case 2:
				draw = draw2;
				break;
			case 3:
				draw = draw3;
				break;
			case 4:
				draw = draw4;
				break;
			case 5:
				draw = draw5;
				break;
		}

		idealProbabilities = new BigDecimal[draw.length];

		for (int i = 1; i < draw.length; i++) {
			idealProbabilities[i] =
					new BigDecimal(draw[i]).divide(divisor, MathContext.DECIMAL128);
		}

		return idealProbabilities;
	}

	public static int[] getFrequencies (boolean isFwr, int handSize) {
		int[] draw1;
		int[] draw2;
		int[] draw3;
		int[] draw4;
		int[] draw5;

		BigDecimal divisor;

		if (!isFwr) {
			draw1 = new int[14];
			draw2 = new int[27];
			draw3 = new int[40];
			draw4 = new int[53];
			draw5 = new int[65];

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

		} else {
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
		}

		switch (handSize) {
			case 1:
				return draw1;
			case 2:
				return draw2;
			case 3:
				return draw3;
			case 4:
				return draw4;
			case 5:
				return draw5;
		}

		return null;
	}

	public static int getTotals(boolean isFwr, int handSize) {

		int[] draw1;
		int[] draw2;
		int[] draw3;
		int[] draw4;
		int[] draw5;
		int total1;
		int total2;
		int total3;
		int total4;
		int total5;


		if (!isFwr) {
			draw1 = new int[14];
			draw2 = new int[27];
			draw3 = new int[40];
			draw4 = new int[53];
			draw5 = new int[65];
			total1 = 0;
			total2 = 0;
			total3 = 0;
			total4 = 0;
			total5 = 0;

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
						total1++;
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
								total2++;
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
										total3++;
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
												total4++;
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
														total5++;
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

		} else {
			draw1 = new int[14];
			draw2 = new int[27];
			draw3 = new int[40];
			draw4 = new int[53];
			draw5 = new int[66];
			total1 = 0;
			total2 = 0;
			total3 = 0;
			total4 = 0;
			total5 = 0;

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
						total1++;
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
								total2++;
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
										total3++;
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
												total4++;
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
													total5++;
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

		switch (handSize) {
			case 1:
				return total1;
			case 2:
				return total2;
			case 3:
				return total3;
			case 4:
				return total4;
			case 5:
				return total5;
		}

		return -1;

	}

	private static int combination(int n, int r) throws RserveException, REXPMismatchException {
		return Rserve.getConnection().eval("factorial(" + n +
				")/(factorial(" + r +
				")*factorial(" + n + "-" + r + "))").asInteger();
	}
}
