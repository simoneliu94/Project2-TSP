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
	private static List<City> parent1List;
	private static List<City> parent2List;
	private static List<City> bestSolution;
	private static ArrayList<List<City>> bestList = new ArrayList<>();
	private static double parent1_distance = 20;
	private static double parent2_distance = 20;
	private static double offspring_distance = 20;
	private static double bestDist = 20;
	private static City aCity = new City();		
	private static int popSize = 1000000;
	private static int count = 0;
	private static int run = 50;
	private static Histogram H;
	
	
	public static void search(List<City> cities, double temp_max, double temp_min) throws FileNotFoundException, UnsupportedEncodingException {
		H = new Histogram(temp_max, temp_min, 100);
		population = population(cities, popSize);		
		
		
		while (count < run) {
			parent1_distance = 20;
			parent1List = cities;
			
			Random rand = new Random();
			int index = rand.nextInt(population.size()-1);
			parent2List = population.get(index);
			parent2_distance = totalDistance(parent2List);			
			
			List<City> offspringList = new ArrayList<>();
			
			mutate();
			
			for (int i = 0; i <= population.size() - 1; i++) {
				tripLength = 0;
				generateDistance(cities,i);

				if (i == population.size() - 1) {					
					
					offspringList = crossOver(minTrip, parent2List);
					
					cities = offspringList;
					population.remove(0);
					population.add(offspringList);
				}				
			}
			
			if(bestList.contains(minTrip) == false && bestList.size()<=run) {				
				bestDist = min;
				bestList.add(minTrip);
				count++;						
			}	

			H.setTripLength(bestDist);
			System.out.println(count);
			
		}
		
		
		printResult();
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
				
				else if (tripLength < parent1_distance) {
					parent1_distance = tripLength;
					List<City> parentList = new ArrayList<>(population.get(index));
					parent1List = parentList;
					
				}
								
				if (tripLength <= 4 && bestList.size()<=run) {
					
					List<City> currentCity = new ArrayList<>(population.get(index));
					
					if(bestList.contains(currentCity) == false) {				
						bestList.add(currentCity);
						bestDist = tripLength;
						count++;						
					}					
				}
				 
				counter += 1;
			}			
			
		}		
	}

	
	public static double totalDistance(List<City> cityList) {
		double total = 0;
		for (int i =0; i<cityList.size()-1; i++) {
			total += aCity.distance(cityList.get(i), cityList.get(i+1));
		}
		total = total + aCity.distance(cityList.get(cityList.size()-1), cityList.get(0));
		return total;
	}
	
	public static List<City> crossOver(List<City> parent1, List<City> parent2){
		List<City> child = new ArrayList<>();
		for (int i = 0; i<7; i++) {
			child.add(parent1.get(i));
		}
		for (int i = 0; i < parent2.size(); i++) {
			if (child.contains(parent2.get(i)) == false) {
				child.add(parent2.get(i));
			}
		}		
		return child;
	}
	
	
	
	public static void mutate() {
		for (int i = 0; i <= population.size() - 1; i++) {
				Random rand = new Random();
				int ind = rand.nextInt(population.get(i).size());
				int ind2 = rand.nextInt(population.get(i).size());
				Collections.swap(population.get(i), ind, ind2);			
		}
	}	
	
	
	
	
	public static void printResult() throws FileNotFoundException, UnsupportedEncodingException {
		/*System.out.println("****Result written to a text file****");
		System.out.println();
		PrintStream fileStream = new PrintStream("GeneticSearch.txt");
		System.setOut(fileStream);*/
		
		for (int i = 0; i<bestList.size(); i++) {
			System.out.println((i+1) + " " + bestList.get(i) + " " + totalDistance(bestList.get(i)));
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
}
