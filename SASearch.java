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
	private static List<City> minDistance;
	private static List<City> maxDistance;
	private static double sum = 0;
	private static double sumSquares = 0;
	private static List<City> currentCity;
	private static City aCity = new City();	
	private static int counter = 0;
	private static double tripLength;
	private static Histogram H;
	

	static void search(List<City> cities, double temperature, double temp_max, double temp_min) throws FileNotFoundException, UnsupportedEncodingException {
		H = new Histogram(temp_max, temp_min, 100);
		
		double coolDownRate = 0.003;
		currentCity = cities;
		
		while (temperature > 1) {
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
			
			if (SASearch.find_probability(tripLength, sum2, temperature) > Math.random()) {
				currentCity = nextCity;
				if (sum2 < min) {
					min = sum2;
					minDistance = nextCity;
				}
			} else if (tripLength < min) {
				min = tripLength;
				minDistance = currentCity;
			}
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
					maxDistance = maxList;
				}
				H.setTripLength(tripLength);
			}
		}
	}
	public static void printResult() throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println("****Result written to a text file****");
		System.out.println();
		PrintStream fileStream = new PrintStream("SASearch.txt");
		System.setOut(fileStream);
		
		double mean = sum/counter;
		double std = Math.sqrt((sumSquares-Math.pow(mean, 2) *counter)/(counter-1));
		
		System.out.println("Mean: " + mean);
		System.out.println("Standard Deviation: " + std);
		System.out.println("Shortest Route: " + minDistance);
		System.out.println("Shortest distance: " + min);
		System.out.println("Longest Route: " + maxDistance);
		System.out.println("Longest distance: " + max);
		
		last = (int) counter;
		H.printHistogram();
	}

	public static double find_probability(double temp, double newTemp, double temperature) {
		if (newTemp < temp) {
			double prob = temperature/(1+Math.log(1+temperature));			
			return prob;
		}
		return Math.exp((temp - newTemp) / temperature);
	}
}
