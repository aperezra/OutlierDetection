package Algorithms;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class Algorithms. 
 *
 * @author Alvaro Perez Ramon
 */
public class Knn implements Algorithm{

	/** The distances. */
	public List<Integer> distances;
	/** The value. */
	public double mean;
	public double nn;

	/**
	 * Instantiates a new algorithms.
	 */
	public Knn() {
		super();
		this.distances = new ArrayList<Integer>();
		this.mean=0;
		this.nn=0;
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

	/* (non-Javadoc)
	 * @see Algorithms.Algorithm#getSize()
	 */
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
	

	/* (non-Javadoc)
	 * @see Algorithms.Algorithm#probOutlier()
	 */
	public double probOutlier() {
		this.nn = 1.0 - this.nn;
		return this.nn;
		
	}
	

	/**
	 * Calculates the distance from X to all the other points in the list.
	 * And stores in a Deque the mean value of the distances to all the other points.
	 * @param data the data
	 */
	public void calculate(Deque<Integer> data){
		distances.clear();
		int calc = 0;
		this.nn=0;
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		//DescriptiveStats ds = new DescriptiveStats(list);
		for (int i=0;i<list.size()-1;i++){
			int dist= distance(list.get(i),list.get(list.size()-1));
			distances.add(dist);
			calc+=dist;
		}
		calc=calc/list.size();
		//double sigma = ds.getStandardDev(mean);
		for (int i=0; i<distances.size()-2;i++){
			if(distances.get(i)<calc){
				this.nn++;
			}
		}
		this.nn=(nn/distances.size());
		this.mean = calc;
	}
	
	
	
//	/**
//	 * Knn.
//	 *
//	 * @param data the data
//	 * @param k the k
//	 * @return the int[]
//	 */
//	public int[] knn(List<Integer> data, int k){
//
//	}
	
	/* (non-Javadoc)
	 * @see Algorithms.Algorithm#deleteFirst()
	 */
	@Override
	public void deleteFirst() {
		distances.remove(0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.distances.toString() +" "+ this.mean + " " +this.nn;	
	}
}
