import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections; 

/**
 * 
 * A class of all matrix operations
 * @author Y M. Liu (Simone)
 *
 */
public class Matrix {
    private int numRows = 0;
    private int numCols = 0;
    private double matrix[][];
    
/**
 * 
 */
    public Matrix() {
    	
    }
/**
 * Represents a matrix in 2 dimensions
 * @param matrix this is a 2D array that takes double
 */
    public Matrix(double[][] data)
    {
        this.numRows = data.length;
        this.numCols = data[0].length;
        this.matrix = new double[numRows][numCols];
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numCols; j++)
            {
                this.matrix[i][j] = data[i][j];
            }
        }        
    }
/**
 * Gets the number of rows and columns and create a new matrix 
 * @param rows is the number of row in the matrix
 * @param cols is the number of column in the matrix
 */
    public Matrix(int rows, int cols)
    {
        this.numRows = rows;
        this.numCols = cols;
        this.matrix = new double[numRows][numCols];
    }
    

/**
 * Gets a value from a matrix from certain row and col    
 * @param row
 * @param col
 * @return
 */
    public double getValue(int row, int col) {
    	return this.matrix[row][col];
    }
 
/**
 * Gets all the data from a row of a matrix    
 * @param row
 * @return 1xn matrix
 */
    public Matrix getRow(int row) {
    	Matrix A = new Matrix(1, numCols);
    	for (int i=0; i<A.numCols; i++)
        {            
            A.matrix[0][i] = this.matrix[row][i];
        }
    	return A;
    }

/**
 * Gets all the data from a column of a matrix    
 * @param col
 * @return nx1 matrix
 */
    public Matrix getCol(int col) {
    	Matrix A = new Matrix(numRows, 1);
    	for (int i=0; i<A.numRows; i++)
        {            
            A.matrix[i][0] = this.matrix[i][col];
        }
    	return A;
    }
    
/**
 * Get the number of rows in a matrix    
 * @return
 */
    public int get_numRow() {
    	return numRows;
    }
 
/**
 * Get the number of cols in a matrix    
 * @return
 */
    public int get_numCol() {
    	return numCols;
    }

/**
 * Adding matrices to a ArrayList    
 * @return
 */
    public ArrayList<Matrix> toClass(){
    	ArrayList<Matrix> w1 = new ArrayList<Matrix>();
    	for (int i = 0; i<this.numRows; i++) { 
    		w1.add(this.getRow(i));    		
    	}
    	return w1;
    }
    
/**
 * Adding traspose matrices to a ArrayList    
 * @return
 */
    public ArrayList<Matrix> toClass_transpose(){
    	Matrix trans = this.transpose();
    	ArrayList<Matrix> w1 = new ArrayList<Matrix>();
    	for (int i = 0; i<trans.numCols; i++) { 
    		w1.add(trans.getCol(i));    		
    	}
    	return w1;
    }

/**
 * Represents a matrix in 2D in String format
 */
    public String toString()
    {
        String output = "";
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numCols; j++)
            {
                output += matrix[i][j] + " ";                
            }
            output += "\n";
        }

        System.out.println(numRows +"x"+ numCols + " matrix");
        return output;
    }
    
    
/**
 * Swaps rows and columns to each other
 * @return a new matrix after swapping
 */
    public Matrix transpose()
    {
        Matrix A = new Matrix(numCols,numRows);
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numCols; j++)
            {
                A.matrix[j][i] = this.matrix[i][j];
            }
        }
        return A;
    }    
/**
 * Adds 2 matrices 
 * @param B is a matrix you want to add to
 * @return a matrix result after adding
 */
    public Matrix add(Matrix B)
    {
        Matrix A = this;
        if(B.numRows != A.numRows || B.numCols != A.numCols)
        {
            System.out.println("CANNOT ADD");
            return null;
        }

        Matrix C = new Matrix(numRows,numCols);
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numCols; j++)
            {
                C.matrix[i][j] = A.matrix[i][j] + B.matrix[i][j];                
            }
        }
        return C;
    }
 
/**
 * Subtracts 2 matrices
 * @param B is a matrix you want to subtract to
 * @return a matrix
 */
    public Matrix subtract(Matrix B) {
    	Matrix A = this;
        if(B.numRows != A.numRows || B.numCols != A.numCols)
        {
            System.out.println("CANNOT SUBTRACT");
            return null;
        }

        Matrix C = new Matrix(numRows,numCols);
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numCols; j++)
            {
                C.matrix[i][j] = A.matrix[i][j] - B.matrix[i][j];                
            }
        }
        return C;
    }
/**
 * Multiplies 2 matrices    
 * @param B is a matrix you want to multiply to
 * @return a matrix result after multiplying
 */
    public Matrix mult(Matrix B)
    {
        Matrix A = this;
        if (A.numCols != B.numRows)
        {
            System.out.println("CANNOT MULTIPLY");
            return null;
        }
        
        Matrix C = new Matrix(A.numRows,B.numCols);
        for (int i=0; i< C.numRows; i++)
        {
            for (int j=0; j<C.numCols; j++)
            {
                for (int m=0; m<A.numCols; m++)
                C.matrix[i][j] += (A.matrix[i][m] * B.matrix[m][j]);
            }
        }
        return C;        
    }
/**
 * Multiplies every number in the matrix by a same number
 * @param scalar is a number
 * @return a matrix result after multiplying
 */
    public Matrix mult(double scalar)
    {
        Matrix A = this;
        Matrix C = new Matrix(numRows,numCols);
        
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numCols; j++)
            {
                C.matrix[i][j] = A.matrix[i][j]*scalar;             
            }
        }
        return C;
    }
/**
 * Compares 2 matrices    
 * @param B is a matrix you want to compare to
 * @return a result if the matrices are equal or not
 */
    public boolean equals(Matrix B)
    {
        Matrix A = this;
        double firstOne[][] = new double[A.numRows][A.numCols];
        double secondOne[][] = new double[B.numRows][B.numCols];
        return Arrays.deepEquals(firstOne, secondOne);
    }
    
/**
 * Create a square identity matrix    
 * @param size
 * @return
 */
    public Matrix create_identity (int size) {
    	Matrix A = new Matrix(size,size);
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			if(i==j) {
    				A.matrix[i][j]=1.0;
    			}
    			else {
    				A.matrix[i][j]=0.0;
    			}
    		}
    	}
    	return A;
    }
    
/**
 * Augment coefficient matrix to create a nx2n or nxn+1 matrix     
 * @param A: given
 * @param I: identity matrix
 * @return
 */
    public Matrix augment(Matrix A, Matrix I) {
    	Matrix C = new Matrix(A.numRows, A.numCols+I.numCols);
    	for (int i = 0; i < C.numRows; i++) {
    		for (int j = 0; j < C.numCols; j++) {
    			if(j < A.numCols) {
    				C.matrix[i][j] = A.matrix[i][j];
    			}
    			else {
    				C.matrix[i][j] = I.matrix[i][j-A.numCols];
    			}
    		}
    	}    	
		return C;    	
    }
 
/**
 * Find the mean vector of a class
 * @param aClass/matrix
 * @return Matrix (the mean of a class) 
 */
    public Matrix find_mean (ArrayList<Matrix> aClass){
    	//Get the first vector out of the class
    	Matrix m1 = aClass.get(0);
    	//Find sum of the class
    	for (int i = 1; i < aClass.size(); i++) {
        	m1 = m1.add(aClass.get(i));
        }       
    	//Find average of the class
        m1 = m1.mult(1.0/aClass.size());
    	
        //Return the mean vector
		return m1;	    	
    }
    
/**
 * Find the covariance matrix
 * @param aClass
 * @return
 */
    public Matrix find_covariance(ArrayList<Matrix> aClass) {
    	//Create a new class to store the result after found the nxn product
    	ArrayList<Matrix> class1 = new ArrayList<Matrix>();
    	
    	//Find mean
    	Matrix mean = find_mean(aClass);
    	
    	//1.Subtract the mean from each vector of the class
    	//2.Multiply the result from 1. to its transpose to find nxn product
    	for (int i = 0; i < aClass.size(); i++) {
    		Matrix m1 = aClass.get(i).subtract(mean);    		
    		Matrix square = m1.mult(m1.transpose());
    		
    		class1.add(square);    		
    	}
    	    	
    	Matrix covariance = find_mean(class1);
    	
		return covariance;    	
    }

/**
 * Swaps 2 rows in matrix
 * @param rowA
 * @param rowB
 */
    public void interchange_row(int rowA, int rowB) {
    	double[] temp_row = matrix[rowA];
    	matrix[rowA] = matrix[rowB];
		matrix[rowB] = temp_row;
    }

/**
 * Implement Gauss Jordan elimination    
 * @param b
 * @return matrix C after applying Gauss Jordan algorithm
 */
    public Matrix gaussJordan(Matrix b) {
    	@SuppressWarnings("unused")
		int E = 1;   
    	int p;
    	Matrix C = augment(this,b);
    	    	
    	for(int j = 0; j < C.numRows; j++) {
    		p = j;
    		//Compute pivot, find max of the column
    		for(int i = j; i < C.numRows; i++) {
    			if (Math.abs(C.matrix[p][j]) < Math.abs(C.matrix[i][j])) {
    				p = i;
    			}
    		}    	
    		
    		if (C.matrix[p][j] == 0) {
				E = 0;
			}
    		
    		//Swap 2 rows j and p
    		if(p > j) {
    			C.interchange_row(j, p);
    		}
    		
    		//Divide row j by Cjj
    		double Cjj = C.matrix[j][j];    
    		for (int i = 0; i < C.numCols; i++) {    						
				C.matrix[j][i] = C.matrix[j][i] / Cjj;
    		}
    			
    		//For each i != j, subtract Cij times row j from row i
    		for (int i = 0; i < C.numRows; i++) {
    			if (i!=j) {
    				double Cij = C.matrix[i][j];
    				for (int m=0; m < C.numCols; m++) {
    					C.matrix[i][m] = C.matrix[i][m] - (C.matrix[j][m] * Cij);
    				}
    			}					
			} 	 
    	}    	
		return C;    	 
    }

/**
 * Find determinant matrix
 * @return the determinant (double type)
 */
    public double find_determinant() {
    	int r = 0;
    	int p;
    	double determinant = 1.0;
    	Matrix A = new Matrix(this.matrix);
    	
    	//Double check on finding determinant matrix
    	//double deter = A.matrix[0][0]*A.matrix[1][1] - A.matrix[0][1]*A.matrix[1][0];
    	//System.out.println(deter);
    	    	
    	for(int j = 0; j < A.numRows; j++) {
    		p = j;
    		//Compute pivot, find max of the column
    		for(int i = j; i < A.numRows; i++) {
    			if (Math.abs(A.matrix[p][j]) < Math.abs(A.matrix[i][j])) {
    				p = i;
    			}
    		}   		
    		
    		if (A.matrix[p][j] == 0) {
    			determinant = 0.0;
			}
    		//Swap 2 rows j and p
    		if(p > j) {
    			A.interchange_row(j, p);
    			r++;    			
    		}
    		
    		//subtract Aij /Ajj times row j from row i
    		for (int i = 0; i < A.numRows; i++) {
    			if(i>j) {
    				double Aij = A.matrix[i][j];
    				double Ajj = A.matrix[j][j];
    				for (int m = 1; m < A.numCols; m++) {
    					A.matrix[i][m] = A.matrix[i][m] - (A.matrix[j][m] * (Aij/Ajj));
    				}    				
    			}
    		}
    	}    	
    	//multiply diagonally 
    	for (int i = 0; i < A.numRows; i++) {
			determinant = determinant * A.matrix[i][i];
		}    	
    	
    	//(-1)^r( A11xA22x…xAnn)
    	determinant = determinant * Math.pow((-1), r);

		return determinant;        	
    }
    

/**
 * Find inverse of a matrix    
 * @return inverse matrix
 */
    public Matrix find_inverse() {
		int E = 1;   
    	int p;
    	Matrix C = augment(this,create_identity(numRows));
    	    	
    	for(int j = 0; j < C.numRows; j++) {
    		p = j;
    		//Compute pivot, find max of the column
    		for(int i = j; i < C.numRows; i++) {
    			if (Math.abs(C.matrix[p][j]) < Math.abs(C.matrix[i][j])) {
    				p = i;
    			}
    		}    	
    		
    		if (C.matrix[p][j] == 0) {
				E = 0;
			}
    		
    		//Swap 2 rows j and p
    		if(p > j) {
    			C.interchange_row(j, p);
    		}
    		
    		//Divide row j by Cjj
    		double Cjj = C.matrix[j][j];    
    		for (int i = 0; i < C.numCols; i++) {    						
				C.matrix[j][i] = C.matrix[j][i] / Cjj;
    		}
    			
    		//For each i != j, subtract Cij times row j from row i
    		for (int i = 0; i < C.numRows; i++) {
    			if (i!=j) {
    				double Cij = C.matrix[i][j];
    				for (int m=0; m < C.numCols; m++) {
    					C.matrix[i][m] = C.matrix[i][m] - (C.matrix[j][m] * Cij);
    				}
    			}					
			} 	 
    	} 
    	
    	//Save inverse matrix since the form is now [A,I]
    	Matrix inverse = new Matrix(C.numRows,C.numCols/2);
    	for (int i = 0; i < inverse.numRows; i++) {
    		for (int j = C.numCols/2; j < C.numCols; j++) {
    			inverse.matrix[i][j-C.numCols/2] = C.matrix[i][j];
    		}
    	}    	
		return inverse;    	
    }

/**
 * Find the discriminant of a matrix    
 * @param points
 * @param mean
 * @param inverse
 * @param det: determinant
 * @return a matrix
 */
    public Matrix find_discriminant(Matrix points, Matrix mean, Matrix inverse, double det) {
    	Matrix g = new Matrix(); 

    	g = points.transpose().subtract(mean.transpose());
    	g = g.mult(-0.5);
    	g = g.mult(inverse);
    	g = g.mult(points.subtract(mean));
    	
    	double g2 = (-0.5*Math.log(det));
    	g2 = g2 + Math.log(0.5);
    	
    	Matrix g3 = new Matrix(new double[][] {{g2}});
    	
    	g = g.add(g3);
    	return g;
    }

/**
 * Find the boundary points between 2 classes    
 * @param aClass
 * @param dis1
 * @param dis2
 * @return
 */
    public void boundary_plot(Matrix point, Matrix dis1, Matrix dis2, ArrayList<Matrix> b_point) {
    	double eps = 0.1;
    	
    	double g1 = dis1.getValue(0,0);
    	double g2 = dis2.getValue(0,0);
    	double mag = Math.abs(g1-g2);
    	
    	if(mag<eps) {
    		b_point.add(point);
    	}    	
    }  

/**
 * Find the condition number of matrices    
 * @param B: matrix
 * @return
 */
    public double find_condition(Matrix B) {
    	double conditionNum = 0.0;
    	Matrix A = new Matrix(this.matrix);
    	double sum = A.normalizeMatrix();
    	double sum2 = B.normalizeMatrix();
    	
    	conditionNum = sum*sum2;
    	return conditionNum;
    }
    
/**
 * 
 * @return
 */
    public double normalizeMatrix() {
    	//Matrix norm = new Matrix(this.matrix);
    	double sum = 0.0;
    	ArrayList<Double>matrix1 = new ArrayList<Double>();
    	
    	//Calculate the sum of absolute values in the row
    	for (int i = 0; i<this.numRows; i++) {
    		sum = 0.0;
    		for (int j = 0; j<this.numCols; j++) {
    			sum = sum + Math.abs(this.matrix[i][j]);
    		}
    		matrix1.add(sum);
    	}    	
    	//Find the max
    	sum = Collections.max(matrix1);
		return sum;    	
    }

    
/**
 *     
 * @param B
 * @return
 */
    public double trace (Matrix B) {
    	double sum = 0;
    	if (B.numRows != B.numCols) {
    		System.out.println("Not a square matrix");
    	}
    	else {
    		for (int i = 0; i<B.numRows; i++) {
        		sum += B.matrix[i][i];
    		}
    	}
    	
		return sum;
    	
    }    
    
/**
 *     
 * @param A
 * @return
 */
    public ArrayList<Double> leverrier (Matrix A){
    	ArrayList<Double> coeff = new ArrayList<Double>();
    	int n = A.numRows;
    	Matrix iden = A.create_identity(A.numCols);  	
    	
    	
    	Matrix B_n = new Matrix(A.matrix);
    	double a_n = -1*A.trace(B_n);
    	
    	System.out.println("The coefficients of the polynomial for the matrix: ");
    	System.out.println(a_n);
    	coeff.add(a_n);
    	
    	for (int k = n-1; k>0; k--) {    		
    		Matrix B_k = A.mult(B_n.add(iden.mult(a_n)));    		
    		double a_k = -1*A.trace(B_k)/(n-k+1);
    		
    		System.out.println(a_k);
    		coeff.add(a_k);
    		
    		B_n = new Matrix(B_k.matrix);
    		a_n = a_k;
    	}    	    	 	
    	
		return coeff;    	
    }
    
/**
 *     
 * @param coeff
 * @return
 */
    public ArrayList<Double> solv_quadratic (ArrayList<Double> coeff){
    	ArrayList<Double> result = new ArrayList<Double>();
		double a = 1.0;
		double b = coeff.get(0);
		double c = coeff.get(1);
		
		double delta = b*b - 4*a*c;
		
		if (delta == 0) {
			double r = -b/(2*a);
			result.add(r);
			System.out.println(result);
		}
		else if (delta > 0) {
			double r1 = (-b + Math.sqrt(delta))/(2*a);
			double r2 = (-b - Math.sqrt(delta))/(2*a);
			result.add(r1);
			result.add(r2);
			System.out.println("lambda1: " + r1);
			System.out.println("lambda2: " + r2);
			System.out.println();
		}
		else {
			System.out.println("Imaginary roots");
		}
		return result;
    	
    }
    
/**
 *     
 * @param lambdas
 */
    public void eigenvector (ArrayList<Double> lambdas) {
    	double lambda1 = lambdas.get(0);
    	double lambda2 = lambdas.get(1);
    	Matrix iden = this.create_identity(this.numCols);  	
    	
    	Matrix sub1 = (iden.mult(lambda1)).subtract(this);
    	Matrix sub2 = (iden.mult(lambda2)).subtract(this);

    	System.out.println("At lambda1 = "+lambda1);
    	System.out.println("x1 = "+ -sub1.getValue(0,1)/sub1.getValue(0,0) + " x2");
    	System.out.println();
    	
    	System.out.println("At lambda2 = "+lambda2);
    	System.out.println("x1 = "+ -sub2.getValue(0,1)/sub2.getValue(0,0) + " x2");
    	System.out.println(); 	    	
    }
    
    
/**
 *     
 * @param A
 * @return
 */
    public double direct_power(Matrix A) {    	
    	Matrix y = new Matrix(A.numRows,1);
    	for (int i=0; i<y.numRows; i++) {
    		for (int j=0; j<y.numCols; j++) {
    			y.matrix[i][j] = 1.0;
    		}
    	}  
    	
    	double epsilon = 0.00002;
    	int m = 1000;
    	int i = 0;    	
    	double e1 = 0.0;    	
    	
    	Matrix x = A.mult(y);        	
    	
		double normX = x.normalizeMatrix();    		
		y = x.mult(1/normX);
		    		
		x = A.mult(y);
		
		Matrix transY = y.transpose();    		
		Matrix u1 = transY.mult(x);    		
		Matrix u2 = transY.mult(y);    		
		double u = u1.getValue(0, 0)/u2.getValue(0, 0);
		
		Matrix r = (y.mult(u)).subtract(x);		
    	
    	while(r.normalizeMatrix()>epsilon && i<m) {	    		
    		normX = x.normalizeMatrix();    		
    		y = x.mult(1/normX);
    		    		
    		x = A.mult(y);
    		
    		transY = y.transpose();    		
    		u1 = transY.mult(x);    		
    		u2 = transY.mult(y);    		
    		u = u1.getValue(0, 0)/u2.getValue(0, 0);
    		
    		r = (y.mult(u)).subtract(x);
    		
    		e1 = u; 
    		i++;   	
    	}  

    	return e1;
    	
    }
    
    
    
}
