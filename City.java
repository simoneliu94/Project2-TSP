public class City {	
	private double x;
	private double y;
	private String name;
	
	
	public City(String cityName, double x_coor, double y_coor) {		
		this.x = x_coor;
		this.y = y_coor;	
		this.name = cityName;
	}
	
	public City() {
		// TODO Auto-generated constructor stub
	}

	public double distance(City city1, City city2) {
		double xDistance = Math.abs(city1.x - city2.x);
		double yDistance = Math.abs(city1.y - city2.y);
		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}
	
	/*public String toString(){
        return this.name +"\t" +this.x +"\t"+  this.y;
    }*/
	
	public String toString(){
        return this.name +" ";
    }
	
	public String getName() {
		return this.name;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
}
