package Algorithms;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;



/**
 * The Class Algorithms. 
 *
 * @author Alvaro Perez Ramon
 */
public class Knn implements Algorithm{

	/** The distances. */
	public List<Integer> distances;


	/**
	 * Instantiates a new algorithms.
	 */
	public Knn() {
		super();
		this.distances = new ArrayList<Integer>();

	}

	/**
	 * Gets the distances.
	 *
	 * @return the distances
	 */
	public List<Integer> getDistances() {
		return distances;
	}

	/**
	 * Sets the distances.
	 *
	 * @param distances the new distances
	 */
	public void setDistances(List<Integer> distances) {
		this.distances = distances;
	}

	/**
	 * Distance.
	 *
	 * @param reference the reference
	 * @param val the val
	 * @return the int
	 */
	@Override
	public int distance(int reference, int val) {
		int distance = Math.abs(val-reference);
		return distance;
	}
	

	@Override
	public double ros() {
//		DescriptiveStats stats = new DescriptiveStats(distances);
//		double dev = stats.getStandardDev(stats.getMean());
//		return dev;
//		//		double ros = 0;
//		
////		if(max!=0){
////			ros = (distances.get(index)/max);
////		}
		return 0.0;
	}
	

	/**
	 * Calculates the distance from X to all the other points in the list.
	 *
	 * @param data the data
	 */
	public void knnX(Deque<Integer> data, int x){
		double max=0;
		Deque<Integer> dist = new ArrayDeque<Integer>();
		for(int i: data){
			dist.add(distance(i,x));		
		}
		DescriptiveStats stats = new DescriptiveStats(dist);
		int meanVal=stats.getMean();
		if(meanVal>max){
			max=meanVal;
		}

		distances.add(meanVal);
	}
	
	/**
	* Calculates the distance from a point to the k previous points.
	*
	* @param data the data/
    * @param k the number of neighbors to calculate
	*/
//	public void knn(List<Integer> data, int k){
//		for(int i=(data.size()-k-1); i<(data.size()-1); i++){
//			int distance = distance(data.get(i),this.value);			
//		}
//	}

	


}
