package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * The Class Algorithms. 
 *
 * @author Alvaro Perez Ramon
 */
public class Knn implements Algorithm{

	/** The distances. */
	public List<Integer> distances;
	public int value;

	/**
	 * Instantiates a new algorithms.
	 */
	public Knn() {
		super();
		this.distances = new ArrayList<Integer>();
		value=0;

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
		Collections.sort(distances);
		double index=distances.indexOf(value)+1;
		double size=distances.size();
		if(index==size || index==size) return 1.5;
		return (index/size);
	}
	

	/**
	 * Calculates the distance from X to all the other points in the list.
	 * And stores in a Deque the mean value of the distances to all the other points.
	 * @param data the data
	 */
	public void calculate(Deque<Integer> data){
		distances.clear();
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		for(int i: list){
			int dist=0;
			for(int j:list){
				if(i!=j){
					dist+=distance(i,j);
				}
			}
			distances.add(dist/data.size());
		}
		value=distances.get(distances.size()-1);
	}

	@Override
	public void deleteFirst() {
		distances.remove(0);
	}

	@Override
	public String toString(){
		return this.distances.toString();	
	}
}
