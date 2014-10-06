package Algorithms;


import java.util.ArrayDeque;
import java.util.Deque;


/**
 * The Class Algorithms. 
 *
 * @author Alvaro Perez Ramon
 */
public class Knn implements Algorithm{

	/** The distances. */
	public Deque<Integer> distances;

	/**
	 * Instantiates a new algorithms.
	 */
	public Knn() {
		super();
		this.distances = new ArrayDeque<Integer>();

	}

	/**
	 * Gets the distances.
	 *
	 * @return the distances
	 */
	public Deque<Integer> getDistances() {
		return distances;
	}

	/**
	 * Sets the distances.
	 *
	 * @param distances the new distances
	 */
	public void setDistances(Deque<Integer> distances) {
		this.distances = distances;
	}

	public int getSize(){
		return distances.size();
	}
	/**
	 * Distance.
	 *
	 * @param reference the reference
	 * @param val the val
	 * @return the int
	 */
	public int distance(int reference, int val) {
		int distance = Math.abs(val-reference);
		return distance;
	}
	


	public double probOutlier() {
		DescriptiveStats ds = new DescriptiveStats(distances);
		double mean = ds.getMean();
		System.out.println(mean);
		double sigma = ds.getStandardDev(mean);
		System.out.println(sigma);
		Gaussian gaussian = new Gaussian(); 
		return gaussian.phi(distances.getLast(),mean,sigma);
	}
	

	/**
	 * Calculates the distance from X to all the other points in the list.
	 * And stores in a Deque the mean value of the distances to all the other points.
	 * @param data the data
	 */
	public void calculate(Deque<Integer> data){
		double max=0;
		Deque<Integer> dist = new ArrayDeque<Integer>();
		for(int i: data){
			dist.addLast(distance(i,data.getLast()));		
		}
		DescriptiveStats stats = new DescriptiveStats(dist);
		int meanVal=stats.getMean();
		if(meanVal>max){
			max=meanVal;
		}
		if(distances.isEmpty()){
			distances.offerFirst(meanVal);
		}
		else{
			distances.offerLast(meanVal);
		}

	}

	@Override
	public void deleteFirst() {
		distances.removeFirst();
	}


}
