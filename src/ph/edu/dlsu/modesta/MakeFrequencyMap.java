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
		int[] draw1 = new int[14];
		int[] draw2 = new int[27];
		int[] draw3 = new int[40];
		int[] draw4 = new int[53];
		int[] draw5 = new int[66];

		for(int i = 1; i <= 5; i++){
			boolean toggle = false;
			for(int j = 1; j <= 52; j++){ //1st draw
				int drawtotal1;
				if(j%13 == 0)
					drawtotal1 = 13;
				else
					drawtotal1 = j%13;
				toggle = false;
				if(i == 1){
					draw1[drawtotal1]++;
					toggle = true;
				}
				if(!toggle){
					for(int k = j + 1; k <=52; k++) { //2nd draw
						int drawtotal2 = drawtotal1;
						if(k%13 == 0)
							drawtotal2 += 13;
						else
							drawtotal2 += k%13;
						toggle = false;
						if(i == 2){
							draw2[drawtotal2]++;
							toggle = true;
						}
						if(!toggle){
							for(int l = k + 1; l <= 52; l++) { //3rd draw
								int drawtotal3 = drawtotal2;
								if(l%13 == 0)
									drawtotal3 += 13;
								else
									drawtotal3 += l%13;
								toggle = false;
								if(i == 3){
									draw3[drawtotal3]++;
									toggle = true;
								}
								if(!toggle) {
									for (int m = l + 1; m <= 52; m++) { //4th draw
										int drawtotal4 = drawtotal3;
										if(m%13 == 0)
											drawtotal4 += 13;
										else
											drawtotal4 += m%13;
										toggle = false;
										if(i == 4){
											draw4[drawtotal4]++;
											toggle = true;
										}
										if(!toggle) {
											for (int n = m + 1; n <= 52; n++) { //5th draw
												int drawtotal5 = drawtotal4;
												if(!(n == j || n == k || n == l || n == m )){
													if(n%13 == 0)
														drawtotal5 += 13;
													else
														drawtotal5 += n%13;
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
		System.out.println("1 CARDS DRAWN");
		for(int i = 1; i <= 13; i++)
			System.out.println(1 + "," + i + "," + draw1[i]);

		System.out.println("2 CARDS DRAWN");
		for(int i = 1; i <= 26; i++)
			System.out.println(2 + "," + i + "," + draw2[i]);

		System.out.println("3 CARDS DRAWN");
		for(int i = 1; i <= 39; i++)
			System.out.println(3 + "," + i + "," + draw3[i]);

		System.out.println("4 CARDS DRAWN");
		for(int i = 1; i <= 52; i++){
			System.out.println(4 + "," + i + "," + draw4[i]);
		}

		System.out.println("5 CARDS DRAWN");
		for(int i = 1; i <= 65; i++)
			System.out.println(5 + "," + i + "," + draw5[i]);

		w.close();
	}
}
