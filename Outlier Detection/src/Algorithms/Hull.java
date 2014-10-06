package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;


public class Hull implements Algorithm{

	public List<Integer> hulls;

	public Hull(){
		super();
		this.hulls=new ArrayList<Integer>();
	}


	public List<Integer> getHulls() {
		return hulls;
	}
	
	public int getSize(){
		return hulls.size();
	}

	public void deleteFirst(){
		hulls.remove(0);
	}

	public double probOutlier() {
		double numHull=0;
		return numHull;
	}

	/**
	 * Hull. Sort the list from lower to higher value so its possible to show the outermost layers.
	 *
	 * @param data the list with the visits
	 * @return the sorted list from lower to higher value
	 */
	public void calculate(Deque<Integer> data){
		if(hulls.isEmpty()){
			hulls = new ArrayList<Integer>(data);
		}
		else{
			hulls.add(data.getLast());
		}
		Collections.sort(hulls);
	}






}
