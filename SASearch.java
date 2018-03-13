import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SASearch {
	static int last = 0;
	static double max = 0;
	static double min = 14.5;
	private static List<City> minTrip;
	private static List<City> maxTrip;
	private static List<City> bestSolution;
	private static double bestDist = 3.6;
	private static int count = 0;
	private static double sum = 0;
	private static double sumSquares = 0;
	private static List<City> currentCity;
	private static City aCity = new City();	
	private static int counter = 0;
	private static double tripLength;
	private static Histogram H;
	

	static void search(List<City> cities, double temperature, double temp_max, double temp_min) throws FileNotFoundException, UnsupportedEncodingException {
		H = new Histogram(temp_max, temp_min, 100);
		
		double coolDownRate = 0.000001;
		currentCity = cities;
		
		while (count<50) {
			tripLength = 0;
			generateMatrix(cities);

			List<City> nextCity = new ArrayList<City>(cities);
			for (int i = 3; i < 12; i++) {
				int index = (int) (nextCity.size() * Math.random());
				int index2 = (int) (nextCity.size() * Math.random());
				Collections.swap(nextCity, index, index2);
			}

			double sum2 = 0;
			for (int i = 0; i <= nextCity.size() - 1; i++) {
				if (i < nextCity.size() - 1) {
					sum2 += aCity.distance(nextCity.get(i),nextCity.get(i + 1));
				} 
				else {
					sum2 += aCity.distance(nextCity.get(i),nextCity.get(0));
				}
			}

			if (SASearch.find_prob(tripLength, sum2, temperature) > Math.random()) {
				currentCity = nextCity;
				if (sum2 < min) {
					min = sum2;
					minTrip = nextCity;
					
				}
				
				if (sum2 <= 4.7) {
					bestDist = sum2;				
					bestSolution = nextCity;
					
					System.out.println ((count+1) + " " +bestSolution);
					System.out.println ("Distance " +bestDist);
					System.out.println ();
					count++;
					
				}
				
				
			} 
			
			else if (tripLength < min) {
				min = tripLength;
				minTrip = currentCity;
				System.out.println ("Distance " +min);
			}
			
			H.setTripLength(bestDist);

			counter++;
			temperature *= 1 - coolDownRate;
		}
		printResult();
		
	}
	
	public static void generateMatrix(List<City> cities) {
		
		for (int i = 0; i <= currentCity.size() - 1; i++) {
			if (i < currentCity.size() - 1) {
				tripLength += aCity.distance(currentCity.get(i),currentCity.get(i + 1));
			} 
			else {
				tripLength += aCity.distance(currentCity.get(i),currentCity.get(0));
				sum += tripLength;
				sumSquares += (tripLength*tripLength);
				
				if (tripLength > max) {
					max = tripLength;
					List<City> maxList= new ArrayList<>(cities);
					maxTrip = maxList;
				}
				
			}
		}
	}
	public static void printResult() throws FileNotFoundException, UnsupportedEncodingException {
		/*System.out.println("****Result written to a text file****");
		System.out.println();
		PrintStream fileStream = new PrintStream("SASearch.txt");
		System.setOut(fileStream);*/
		
		double mean = sum/counter;
		double std = Math.sqrt((sumSquares-Math.pow(mean, 2) *counter)/(counter-1));
		
		System.out.println("Mean: " + mean);
		System.out.println("Standard Deviation: " + std);
		System.out.println("Shortest Route: " + minTrip);
		System.out.println("Shortest distance: " + min);
		System.out.println("Longest Route: " + maxTrip);
		System.out.println("Longest distance: " + max);
		
		last = (int) counter;
		H.printHistogram();
	}

	public static double find_prob(double temp, double newTemp, double temperature) {
		if (newTemp < temp) {
			double prob = temperature/(1+Math.log(1+temperature));			
			return 0.8;
		}
		return Math.exp((temp - newTemp) / temperature);
	}
}
