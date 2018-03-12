import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExhaustiveSearch {
	private static long counter = 0;
	static int last = 0;
	static double maxDistance = 0;
	static double minDistance = 14.5;
	private static List<City> minTrip;
	private static List<City> maxTrip;
	private static double tripLength;
	private static double sum = 0;
	private static double sumSquares = 0;
	private static City aCity = new City();
	private static Histogram H;

	
	public static void search(List<City> cities, int start, double temp_max, double temp_min) throws FileNotFoundException, UnsupportedEncodingException {
		
		H = new Histogram(temp_max, temp_min, 100);
		
		generateDistance(cities,start, temp_max, temp_min);
		
		printResult(cities);
		
	}
	
	public static void generateDistance(List<City> cities, int start, double temp_max, double temp_min) {
		for (int i = start; i < cities.size(); i++) {			
			Collections.swap(cities, i, start);				
			generateDistance(cities, start + 1, temp_max, temp_min);				
			Collections.swap(cities, start, i);			
		}
		

		if (start == cities.size() - 1) {
			tripLength = 0;			
			for (int i = 0; i <= cities.size() - 1; i++) {				
				if (i < cities.size() - 1) {
					tripLength += aCity.distance(cities.get(i),cities.get(i + 1));						
				} 
				else {					
					tripLength += aCity.distance(cities.get(i),cities.get(0));					
					sum += tripLength;
					sumSquares += (tripLength*tripLength);
					if (tripLength < minDistance) {
						minDistance = tripLength;
						List<City> minList= new ArrayList<>(cities);
						minTrip = minList;
					}
					if (tripLength > maxDistance) {
						maxDistance = tripLength;
						List<City> maxList= new ArrayList<>(cities);
						maxTrip = maxList;
					}					
					counter += 1;			
					H.setTripLength(tripLength);
				}					
				
			}			
			
		}
	}

	
	public static void printResult(List<City> cities) throws FileNotFoundException, UnsupportedEncodingException {		
		
		if(last!= counter && counter == ExhaustiveSearch.factorial(cities.size()))  {			
			
			System.out.println("****Result written to a text file****");
			System.out.println();
			PrintStream fileStream = new PrintStream("ExhaustiveSearch.txt");
			System.setOut(fileStream);
			
			double mean = sum/counter;
			double std = Math.sqrt((sumSquares-Math.pow(mean, 2) *counter)/(counter-1));
			
			System.out.println("Mean: " + mean);
			System.out.println("Standard Deviation: " + std);
			System.out.println("Shortest Route: " + minTrip);
			System.out.println("Shortest distance: " + minDistance);				
			System.out.println("Longest Route: " + maxTrip);
			System.out.println("Longest distance: " + maxDistance);
			System.out.println(cities.size() + "!: " +counter);
			last = (int) counter;			
			System.out.println();
			
			H.printHistogram(); //print one time only
		}		
	}	
	
	
	public static long factorial(long n) {
		long result = 1;
		for(long i = n; i>0; i--) {
			result *= i;
		}
		return result;
    }
}
