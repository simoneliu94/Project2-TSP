import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class Histogram {
	private double a;
	private double b;
	private double dx;
	private int binNum;
	private long[] histogram;
	
	public Histogram() {
		
	}
	
	public Histogram(double bMax, double aMin, int bins){
		b = bMax;
		a = aMin;
		binNum = bins;
		dx = (b-a)/bins;
		histogram = new long[bins];
		//System.out.println(b + "\t" + a);
		//System.out.println(dx);
	}
	
	public double getDx() {
		return this.dx;
	}
	
	public void setTripLength(double tripLength){
		double binIndex = (tripLength-a)/dx;
		histogram[((int)binIndex)] ++;
		//System.out.println(tripLength + "\t" + a + "\t" + dx);
		//System.out.println("INDEXXXXXXX"+(int)binIndex);
		//System.out.println(histogram[((int)binIndex)] ++);
	}


	public long[] getHistogram(){
		return histogram;
	}
	
	public void printHistogram() throws FileNotFoundException, UnsupportedEncodingException{
		DecimalFormat df = new DecimalFormat("#.##");

		for(int i = 0; i < binNum; i++){
			System.out.println(df.format(i*dx+a) +"-" + df.format((i+1)*dx+a) + "\t" + (histogram[(int)i]));	
		}		
	}

}
