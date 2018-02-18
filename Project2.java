import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * 
 * CSC340 - Project 2
 * @author Y M. Liu (Simone)
 *
 */
public class Project2 {
	
	private static ArrayList<Matrix> class1 = new ArrayList<Matrix>();

    @SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
        Matrix a1 = new Matrix();      
        
        
        
        //------------------------------------------------------------------------------
        
        //Read in the text file
        FileReader inputFile = new FileReader("Resources/eigendataS2018.txt");
		Scanner fileReader = new Scanner(inputFile);
		String line = "";
		
		//While the text file is not empty
		while (fileReader.hasNext()) {
			line = fileReader.nextLine();
			Scanner stringReader = new Scanner(line);
			
			//Create 2x1 vectors
			double[][] vector1 = new double[2][1];
			
			//Add vectors to class1 and class2
			vector1[0][0] = stringReader.nextDouble();
			vector1[1][0] = stringReader.nextDouble();
			class1.add(new Matrix(vector1));
			
		}

		fileReader.close();	
        
		System.out.println("-----------------------------------------------------------------------------------");
        
        //1.a.i
        Matrix mean = a1.find_mean(class1);
        System.out.println("Mean vector:");
        System.out.println(mean);
        
        
        Matrix cov = a1.find_covariance(class1);
        System.out.println("Covariance:");
        System.out.println(cov);
        
		System.out.println("-----------------------------------------------------------------------------------");
        //1.a.ii
        double trace = a1.trace(cov);
        System.out.println("Trace: "+ trace);
        System.out.println();
        
		System.out.println("-----------------------------------------------------------------------------------");
        //1.a.iii
        double deter = cov.find_determinant();
        System.out.println("Determinant: "+ deter);
        System.out.println();
          
		System.out.println("-----------------------------------------------------------------------------------");
        //1.a.iv
        ArrayList<Double> coeff = a1.leverrier(cov);
        System.out.println("lambda^2 " + coeff.get(0)+"*lambda + " + coeff.get(1));
        System.out.println();
        ArrayList<Double> lambdas = a1.solv_quadratic(coeff);    
        
        double lam1 = a1.direct_power(cov);
        System.out.println("power lambda1: "+lam1);
        double lam2 = a1.trace(cov)-lam1;
        System.out.println("power lambda2: "+lam2);
        
		System.out.println("-----------------------------------------------------------------------------------");
		//1.a.v        
        cov.eigenvector(lambdas);     
        
        
        System.out.println("-----------------------------------------------------------------------------------");
        //2.ii
        Matrix matrixA = new Matrix(new double [][]
            	{{4.633333,56.3,-163.433333,91.1,25.2},
            	{1,0,0,0,0},
            	{0,1,0,0,0},
            	{0,0,1,0,0},
            	{0,0,0,1,0}});	
        
        System.out.println(matrixA);       
        ArrayList<Double> A_coeff = a1.leverrier(matrixA);
        
        
        System.out.println("-----------------------------------------------------------------------------------");
        //2.iii
        double largest_e = a1.direct_power(matrixA);
        System.out.println("The largest eigenvalue for the matrix A: "+largest_e);  
        
        System.out.println("-----------------------------------------------------------------------------------");
        //2.iv        
        Matrix x4 = new Matrix(new double [][]
            	{{11.63333,-25.13333,12.5,3.6},
            	{1,0,0,0},
            	{0,1,0,0},
            	{0,0,1,0}});	
        
        ArrayList<Double> x4_coeff = a1.leverrier(x4);
        System.out.println();
        double x4_e1 = a1.direct_power(x4);
        System.out.println("x4_e1: " + x4_e1);
        
        System.out.println("-----");
        
        Matrix x3 = new Matrix(new double [][]
            	{{2.63333,-1.43333,-0.4},
            	{1,0,0},
            	{0,1,0}});	
        
        ArrayList<Double> x3_coeff = a1.leverrier(x3);
        System.out.println();
        double x3_e1 = a1.direct_power(x3);
        System.out.println("x3_e1: " + x3_e1);
        
        System.out.println("-----");
        
        Matrix x2 = new Matrix(new double [][]
            	{{1.3,0.3},
            	{1,0}});	
        
        ArrayList<Double> x2_coeff = a1.leverrier(x2);
        System.out.println();
        double x2_e1 = a1.direct_power(x2);
        System.out.println("x2_e1: " + x2_e1);
        
        
        /*System.out.println("Testing--------------------------------------------------------------");
        Matrix testing = new Matrix(new double [][]
            	{{6,-11,6},
            	{1,0,0,},
            	{0,1,0}});	
        double e_testing = a1.direct_power(testing);
        System.out.println("e_testing: " + e_testing);*/
        
    }  
}