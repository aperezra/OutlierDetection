package Algorithms;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.CopyOnWriteArrayList;




public class Density implements Algorithm {

	public Deque<Double> densities;
	public double max;
	public double lrdO;
	public double lrdP;
	
	public Density() {
		super();
		this.densities = new ArrayDeque<Double>();
		this.max = 0;
		this.lrdO=0;
		this.lrdP=0;
	}

	public Deque<Double> getDensities() {
		return densities;
	}

	public void setDensities(Deque<Double> densities) {
		this.densities = densities;
	}

	public double getMax() {
		return max;
	}
		
	public void setMax(double max) {
		this.max = max;
	}
	
	public int getSize(){
		return densities.size();
	}
	
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
	public void calculate(Deque<Integer> data){
//		densities.clear();
//		double density = 0;
//		double sum = 0;
//		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
//		int p = new DescriptiveStats(data).getMean();
//		for(int i: list){
//			for(int j: list){
//				if(i!=j)
//				sum+= Math.abs(distance(j,p) - distance(i,p));
//			}
//			density=(list.size()-1)/sum;
//			if(density>max)max=density;
//			densities.add(density);
//		}
		densities.clear();
	//	double density = 0;
		double sumP = 0;
		double sumO = 0;
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		int o = new DescriptiveStats(data).getMean();
		for(int i=0;i<list.size();i++){
			if(i!=list.size()-1){
				sumP+= distance(list.get(i),list.get(list.size()-1));
			}
			sumO+= distance(i,o);
		}
		this.max=list.size()-1;
		this.lrdP=(list.size()-1)/sumP;
		this.lrdO=(list.size()-1)/sumO;
	}
	
	
	
	public void calcMax(){
		for (double d:densities){
			if(d>max){
				max=d;
			}
		}
	}
	
	//The bigger ros is, the likelier is to be an outlier
	public double probOutlier() {
//		if(!densities.contains(max)){
//			calcMax();
//		}
//		double ros = 0;
//		if(max!=0){
//			ros = 1 - (densities.getLast()/max);
//		}
//		//We do the following, to avoid to detect an outlier when the most of the visits are 0;
//		if(ros==1 && max<2){ 
//			ros=0;
//		}
//		return ros;
		double lof = ((this.lrdP/this.lrdO)/max);
		return lof;
	}

	@Override
	public void deleteFirst() {
		densities.removeFirst();
	}

	@Override
	public String toString(){
		return this.densities.toString();	
	}



}
