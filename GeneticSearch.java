import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticSearch {
	private static ArrayList<List<City>> population;
	private static double tripLength;
	private static double sum;
	private static double sumSquares;
	private static double min = 14.5;
	private static List<City> minTrip;
	private static double max = 0;
	private static List<City> maxTrip;
	private static double counter = 1;
	private static List<City> offspringList;
	private static List<City> bestSolution;
	private static ArrayList<List<City>> bestList = new ArrayList<>();
	private static double offSpringDist = 20;
	private static double bestDist = 3.6;
	private static City aCity = new City();		
	private static int popSize = 5;
	private static int count = 0;
	private static double good = 3.6;
	private static Histogram H;
	
	
	public static void search(List<City> cities, int iter, double temp_max, double temp_min) throws FileNotFoundException, UnsupportedEncodingException {
		H = new Histogram(temp_max, temp_min, 100);
		population = population(cities, popSize);

		while (count < 50) {
			offSpringDist = 20;
			offspringList = cities;
			
			List<City> mutationList = new ArrayList<>();
			
			mutate();
			
			for (int i = 0; i <= population.size() - 1; i++) {
				tripLength = 0;
				generateDistance(cities,i);

				if (i == population.size() - 1) {
					//crossOver(cities, mutationList);
					
					mutationList.add(minTrip.get(0));
					for (int p = 5; p < 10; p++) {
						mutationList.add(minTrip.get(p));
					}
					for (int p = 0; p < cities.size(); p++) {
						if (mutationList.contains(offspringList.get(p)) == false){
							mutationList.add(offspringList.get(p));
						}
					}
					
					cities = mutationList;
					population.remove(0);
					population.add(mutationList);
				}
			}			

			H.setTripLength(bestDist);
			
		}
		printResult();
	}
	
	public static void crossOver(List<City> cities, List<City> mutationList) {		
		for (int p = 0; p < 6; p++) {
			mutationList.add(minTrip.get(p));
			System.out.println(mutationList);
		}
		for (int p = 0; p < cities.size(); p++) {
			if (mutationList.contains(offspringList.get(p)) == false){
				mutationList.add(offspringList.get(p));
				System.out.println(mutationList);
			}
		}
		cities = mutationList;
		population.remove(0);
		population.add(mutationList);
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
	
	public static void mutate() {
		for (int i = 0; i <= population.size() - 1; i++) {
				Random rand = new Random();
				int ind = rand.nextInt(population.get(i).size());
				int ind2 = rand.nextInt(population.get(i).size());
				Collections.swap(population.get(i), ind, ind2);			
		}
	}
	
	public static void generateDistance(List<City> cities, int index) {
		for (int j = 0; j <= population.get(index).size() - 1; j++) {
			if (j < cities.size() - 1) {
				tripLength += aCity.distance(population.get(index).get(j),population.get(index).get(j + 1));
			} 
			else {
				tripLength += aCity.distance(population.get(index).get(j),population.get(index).get(0));
				sum += tripLength;
				sumSquares += (tripLength * tripLength);
				
				if (tripLength < min) {
					min = tripLength;
					List<City> minList = new ArrayList<>(population.get(index));
					minTrip = minList;
				} 
				
				else if (tripLength < offSpringDist) {
					offSpringDist = tripLength;
					List<City> parentList = new ArrayList<>(population.get(index));
					offspringList = parentList;
				}
				
				if (tripLength <= 4) {
					bestDist = tripLength;
					List<City> whatever = new ArrayList<>(population.get(index));
					
					if(bestList.contains(whatever) == false) {				
						bestSolution = new ArrayList<>(population.get(index));
						System.out.println ((count+1) + " " +bestSolution);
						System.out.println ("Distance " +bestDist);
						System.out.println ();
						count++;
					}
					
					bestList.add(whatever);
				}

				if (tripLength > max) {
					max = tripLength;
					List<City> maxList = new ArrayList<>(population.get(index));
					maxTrip = maxList;
				}
				 
				counter += 1;
			}
		}
	}
	
	public static void printResult() throws FileNotFoundException, UnsupportedEncodingException {
		/*System.out.println("****Result written to a text file****");
		System.out.println();
		PrintStream fileStream = new PrintStream("GeneticSearch.txt");
		System.setOut(fileStream);*/
		
		double mean = sum / counter;
		double std = Math.sqrt((sumSquares - Math.pow(mean, 2) * (counter)) / ((counter) - 1));
		
		System.out.println("Mean: " + mean);
		System.out.println("Standard Deviation: " + std);
		System.out.println("Shortest Route: " + minTrip);
		System.out.println("Shortest distance: " + min);
		System.out.println("Longest Route: "+ maxTrip);
		System.out.println("Longest distance: " + max);	
		System.out.println();
		
		H.printHistogram();
	}
}
