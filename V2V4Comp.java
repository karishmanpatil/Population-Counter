import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class V2V4Comp {
public static final CensusData cd = PopulationQuery.parse("CenPop2010.txt");
	
	public static void main(String[] args) {
		long start, end;
		Version2 sip = new Version2(cd, 100, 500);
		Version4 smp = new Version4(cd, 100, 500);
		
		for (int i = 0; i < 5; i++)
			sip.gridPopulation(1, 1, 100, 500);
		
		int[] sides = new int [4];
		int[] res = new int[2];
		int counter = 1;
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File("values.txt"));
		} catch (FileNotFoundException e1) {
			System.exit(1);
		}
		
		while (scanner.hasNextLine()) {
			System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
			String input = scanner.nextLine();
			String[] t = input.split(" ");
			if (t.length != 4) {
				scanner.close();
				System.exit(1);
			}
			for (int i = 0; i < t.length; i++) {
				try {
					sides[i] = Integer.parseInt(t[i]);
				} catch(NumberFormatException e) {
					scanner.close();
					System.exit(1);
				}
			}
			
			start = System.currentTimeMillis();
			try {
				res = sip.gridPopulation(sides[0], sides[1], sides[2], sides[3]);
			} catch(IllegalArgumentException e) {
				scanner.close();
				System.exit(1);
			}
			end = System.currentTimeMillis();
			System.out.println("Version 2, " + counter + " time: " + (end - start));
			
			System.out.println("population of rectangle: " + res[0]);
			System.out.format("percent of total population: %.2f", Math.round(10000.0 * res[0] / res[1]) / 100.0);
			System.out.println();
			
			start = System.currentTimeMillis();
			try {
				res = smp.gridPopulation(sides[0], sides[1], sides[2], sides[3]);
			} catch(IllegalArgumentException e) {
				scanner.close();
				System.exit(1);
			}
			end = System.currentTimeMillis();
			System.out.println("Version 4, " + counter + " time: " + (end - start));
			
			System.out.println("population of rectangle: " + res[0]);
			System.out.format("percent of total population: %.2f", Math.round(10000.0 * res[0] / res[1]) / 100.0);
			System.out.println();
			
			counter++;
		}
	}
}