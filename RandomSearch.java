//Simone Liu
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomSearch {
	static Random rand = new Random();
	private static int iter = 1000000;
	private static City aCity = new City();	
	private static double tripLength;
	private static double sumSquares;
	private static double sum;	
	private static double minDistance = 14.5;
	private static double maxDistance = 0;
	private static List<City> minTrip;
	private static List<City> maxTrip;
	private static Histogram H;
	
	public static void search(List<City> cities, double temp_max, double temp_min) throws FileNotFoundException, UnsupportedEncodingException {	
		H = new Histogram(temp_max, temp_min, 100);
		for (int i=0; i<iter;i++) {							
			List<City> tempCity = new ArrayList<City>(cities);				
			Collections.shuffle(tempCity);		
			generateDistance(cities);			
		}		
		printResult();		
	}
	
	public static void generateDistance(List<City> cities) {
		List<City> tempCity = new ArrayList<City>(cities);				
		Collections.shuffle(tempCity);
		
		tripLength = 0;		
		for (int j = 0; j <= cities.size() - 1; j++) {
			if (j < cities.size() - 1) {
				tripLength += aCity.distance(tempCity.get(j),tempCity.get(j + 1));
			} 
			else {
				tripLength += aCity.distance(tempCity.get(j),tempCity.get(0));
				sum += tripLength;
				sumSquares += (tripLength*tripLength);
				
				if (tripLength < minDistance) {
					minDistance = tripLength;
					List<City> minList= new ArrayList<>(tempCity);
					minTrip = minList;
				}
				
				if (tripLength > maxDistance) {
					maxDistance = tripLength;
					List<City> maxList= new ArrayList<>(tempCity);
					maxTrip = maxList;
				}					
			}					
		}		
		
		H.setTripLength(tripLength);
		cities = tempCity;				
	}
	
	
	public static void printResult() throws FileNotFoundException, UnsupportedEncodingException {
		
		System.out.println("****Result written to a text file****");		
		PrintStream fileStream = new PrintStream("RandomSearch.txt");
		System.setOut(fileStream);
		
		double mean = sum/iter;
		double std = Math.sqrt((sumSquares-Math.pow(mean, 2)*iter)/(iter-1));
		
		System.out.println("Mean: " + mean);
		System.out.println("Standard Deviation: " + std);
		System.out.println("Shortest Route: " + minTrip);
		System.out.println("Shortest distance: " + minDistance);
		System.out.println("Longest Route: " + maxTrip);
		System.out.println("Longest distance: " + maxDistance);
		System.out.println("Iteration: " + iter);
		System.out.println();
				
		H.printHistogram(); //print one time only
	}
}