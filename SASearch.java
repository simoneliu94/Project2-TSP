import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SASearch {
	static int last = 0;
	static double max = 0;
	static double min = 14.5;
	private static ArrayList<List<City>> population;
	private static List<City> minTrip;
	private static List<City> maxTrip;
	private static ArrayList<List<City>> bestList = new ArrayList<>();;
	private static double bestDist = 3.6;
	//private static List<City> best;
	private static int count = 1;
	private static double sum = 0;
	private static double sumSquares = 0;
	//private static List<City> currentCity;
	private static City aCity = new City();	
	private static int run = 0;
	//private static double currentDistance;
	private static Histogram H;
	

	static void search(List<City> cities, double temperature, double temp_max, double temp_min) throws FileNotFoundException, UnsupportedEncodingException {
		H = new Histogram(temp_max, temp_min, 100);
		
		double coolDownRate = 0.000025;
		population = population(cities, 1000000);

		List<City> currentRoute = generateCity(cities);
		
		if (totalDistance(currentRoute)<4.0) {
			bestList.add(currentRoute);
		}
		
		List<City> best = new ArrayList<City>(currentRoute);
		System.out.println("Inital route: " + currentRoute);
		System.out.println("Inital distance: " + totalDistance(currentRoute) + "\n");
		
		while (count<51) {				
			
			List<City> nextRoute = new ArrayList<City>(currentRoute);
			
			int index = randomInt(0 , nextRoute.size());
	        int index2 = randomInt(0 , nextRoute.size());
			
	        while(index == index2) {
	        	index2 = randomInt(0 , nextRoute.size());
	        }
	        
	        Collections.swap(nextRoute, index, index2);
	        
	        double currentDistance = totalDistance(currentRoute);
            double nextDistance = totalDistance(nextRoute);
    
            
			if (SASearch.find_probability(currentDistance, nextDistance, temperature) > Math.random()) {
				currentRoute = new ArrayList<City>(nextRoute);		
				
				if (nextDistance <= 4.5) {
					bestDist = nextDistance;				
					bestList.add(nextRoute);
					count++;					
				}
			}
			
			else if (totalDistance(currentRoute) <= totalDistance(best)) {
				best = new ArrayList<City>(currentRoute);	
				if(bestList.contains(best) == false) {
					bestList.add(best);
					System.out.println("No"+ count + " "+totalDistance(best) + " "+ best);
					count++;
				}	
				
			}		
			
			H.setTripLength(bestDist);

			temperature -= coolDownRate;
			}
		
		printResult();
		
	}
	
	public static List<City> generateCity(List<City> cities){
		List<City> tempCity = new ArrayList<City>(cities);
		Collections.shuffle(tempCity);
		return tempCity;
			
	}
	
	public static ArrayList<List<City>> population (List<City> cities, int popSize){
		ArrayList<List<City>> populationCity = new ArrayList<List<City>>();		
		for (int i = 0; i < popSize; i++) {
			List<City> tempCity = new ArrayList<City>(cities);
			Collections.shuffle(tempCity);
			populationCity.add(tempCity);			
		}			
		return populationCity;		
	}	

	public static int randomInt(int min , int max) {
		Random r = new Random();
		double d = min + r.nextDouble() * (max - min);
		return (int)d;
	}

	
	public static double totalDistance(List<City> cityList) {
		double total = 0;
		for (int i =0; i<cityList.size()-1; i++) {
			total += aCity.distance(cityList.get(i), cityList.get(i+1));
		}
		total = total + aCity.distance(cityList.get(cityList.size()-1), cityList.get(0));
		return total;
	}
	
	public static void printResult() throws FileNotFoundException, UnsupportedEncodingException {
		/*System.out.println("****Result written to a text file****");
		System.out.println();
		PrintStream fileStream = new PrintStream("SASearch.txt");
		System.setOut(fileStream);*/
		
		System.out.println();	
		for (int i = 0; i<bestList.size(); i++) {
			System.out.println((i+1) + " " + bestList.get(i) + " " + totalDistance(bestList.get(i)) + "\n");
		}
		
		double sum = 0;
		double sumSquare = 0;
		double temp_std = 0;
		
		for (int i=0; i<bestList.size(); i++) {
			sum = sum + totalDistance(bestList.get(i));
			sumSquare = sumSquare + (sum*sum);			
		}
		
		double mean = sum/bestList.size();
		
		for (int i=0; i<bestList.size(); i++) {
			temp_std += Math.pow(totalDistance(bestList.get(i)) - mean, 2);	
		}		
		
		double std = Math.sqrt(temp_std/bestList.size());

		double min = 14.5;
		List<City> minTrip = null;
		double max = 0;
		List<City> maxTrip = null;
		
		for (int i=0; i<bestList.size(); i++) {
			double dis = totalDistance(bestList.get(i));
			if (min > dis) {
				min = dis;
				minTrip = bestList.get(i);
			}
			if (max < dis) {
				max = dis;
				maxTrip = bestList.get(i);
			}
		}
		
		System.out.println();	
		System.out.println("Mean: " + mean);
		System.out.println("Standard Deviation: " + std);
		System.out.println("Shortest Route: " + minTrip);
		System.out.println("Shortest distance: " + min);
		System.out.println("Longest Route: "+ maxTrip);
		System.out.println("Longest distance: " + max);	
		System.out.println();		
		
		H.printHistogram();
	}

	public static double find_probability(double temp, double newTemp, double temperature) {
		if (newTemp < temp) {
			double prob = temperature/(1+Math.log(1+temperature));			
			return 1.0;
		}
		return Math.exp((temp - newTemp) / temperature);
	}
}
