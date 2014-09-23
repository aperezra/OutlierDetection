package Algorithms;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;



public class Density implements Algorithm {

	public List<Double> densities;
	public double max;
	
	public Density() {
		super();
		this.densities = new ArrayList<Double>();
		this.max = 0;
	}

	public List<Double> getDensities() {
		return densities;
	}

	public void setDensities(List<Double> densities) {
		this.densities = densities;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
	
	
	@Override
	public int distance(int reference, int val) {
		int distance = Math.abs(val-reference);
		return distance;

	}

	/**
	 * Density.
	 *
	 * @param data the data
	 * @param p reference point to which is calculated the density
	 * @param x the point to which we are calculating the density
	 */
	public void densityX(Deque<Integer> data, int p, int x){
		double density = 0;
		double sum = 0;
		for(int i=0; i<data.size();i++){
			sum+= Math.abs(distance(data.getFirst(),p) - distance(x,p));
		}
		if(sum!=0){
			int k = data.size()-1;
			density=k/sum;
			if (density>max){
				max = density;
			}
			densities.add(density);
		}
		else{
			densities.add(0.0);	
		}
	}
	
	
	@Override
	public double ros(int index) {
		double ros = 0;
		if(max!=0){
			ros = 1 - (densities.get(index)/max);
		}
		return ros;
	}





}