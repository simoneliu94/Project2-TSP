import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class TravelingSalesman {
	static ArrayList<City> cities = new ArrayList<City>();
	static ArrayList<Matrix> coordinate = new ArrayList<Matrix>();
	static Matrix mat = new Matrix();
	static Matrix distanceMatrix;
	public static int option;
	public static double temp_max;
	public static double temp_min;
			
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {			
		TravelingSalesman tsp = new TravelingSalesman();
		
		tsp.readIn();		
		tsp.find_tempMaxMin(distanceMatrix,cities.size());
		
		System.out.println("Choose an algorithm:");
		System.out.println("1. Exhaustive Search");
		System.out.println("2. Random Search");
		System.out.println("3. Genetic Algorithm Search");
		System.out.println("4. Simulated Annealing Search");
		
		System.out.print("Type your option # here: ");
		Scanner scan = new Scanner(System.in);
		
		option = scan.nextInt();

		if (option == 1) {
			System.out.println("\n" +"Exhaustive Search......."+ "\n");
			long startTime = System.currentTimeMillis();
			ExhaustiveSearch.search(cities, 0, temp_max, temp_min);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("\n" + "Runtime: " +totalTime + " milliseconds");
			return;
		}

		if (option == 2) {
			System.out.println("\n" +"Random Search......" + "\n");
			long startTime = System.currentTimeMillis();			
			RandomSearch.search(cities, temp_max, temp_min);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("\n" +  "Runtime: " +totalTime + " milliseconds");
			return;
		}

		if (option == 3) {
			System.out.println("\n" +"Genetic Algorithm Search......"+ "\n");
			long startTime = System.currentTimeMillis();			
			GeneticSearch.search(cities, temp_max, temp_min);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("\n" +  "Runtime: " +totalTime + " milliseconds");
			return;
		}

		if (option == 4) {
			System.out.println("\n" +"Simulated Annealing Search......"+ "\n");
			long startTime = System.currentTimeMillis();
			SASearch.search(cities, 100000, temp_max, temp_min);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("\n" +  "Runtime: " +totalTime + " milliseconds");
			return;
		}

		else
			System.out.println("Invalid input!");
		return;
	
	}




	//-------------------------Functions------------------------------
	
	public void readIn() throws FileNotFoundException {
		//Read in the text file
	    FileReader inputFile = new FileReader("Resources/tspdata.txt");
		Scanner fileReader = new Scanner(inputFile);
		String line = "";
		
		//While the text file is not empty
		while (fileReader.hasNext()) {
			line = fileReader.nextLine();
			Scanner stringReader = new Scanner(line);
			
			//Create 2x1 vectors
			double[][] points = new double[2][1];
			String[] city_name = new String [1]; 
			
			//Add vectors to class1 and class2
			points[0][0] = stringReader.nextDouble();
			points[1][0] = stringReader.nextDouble();
			city_name[0] = stringReader.next();
			coordinate.add(new Matrix(points));
			City a_city = new City(city_name[0], points[0][0], points[1][0]);
			cities.add(a_city);
			
		}
		fileReader.close();	
		
		distanceMatrix = mat.find_distanceMatrix(cities);
		System.out.println(distanceMatrix);		

	}
	
	public void find_tempMaxMin(Matrix distanceMat, int numCity) {
		temp_max = 0;
		temp_min = 20;
		for (int i=0; i<distanceMat.get_numRow(); i++) {
			for (int j=0; j<distanceMat.get_numCol(); j++) {
				if (temp_max < distanceMat.getValue(i, j)) {
					temp_max = distanceMat.getValue(i, j);
				}
				if (temp_min > distanceMat.getValue(i, j) && distanceMat.getValue(i, j)!=0) {
					temp_min = distanceMat.getValue(i, j);
				}
				
			}
		}
		temp_max = temp_max*numCity;
		temp_min = temp_min*numCity;
	}
	
	
}
